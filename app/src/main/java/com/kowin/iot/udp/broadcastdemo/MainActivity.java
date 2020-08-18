package com.kowin.iot.udp.broadcastdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.knowin.iot.logger.LoggerFactory;
import com.kowin.iot.udp.broadcastdemo.utils.DeviceUtils;
import com.kowin.iot.udp.broadcastdemo.utils.NetworkUtils;

import cn.geekcity.xiot.nearby.vertx.NearbyDiscoveryListener;
import cn.geekcity.xiot.nearby.vertx.NearbyManager;
import cn.geekcity.xiot.nearby.vertx.typedef.node.NodeInfo;
import io.vertx.core.Vertx;

public class MainActivity extends AppCompatActivity implements NearbyDiscoveryListener {

    private static final String TAG = "MainActivity";
    private NearbyManager nearby;
    private RecyclerView recyclerView;
    private NodeAdapter adapter;
    private boolean started = false;
    private NodeInfo node;
    private String type = "urn:knowin-spec:device:gateway:00000001:know:insight:1";
    private String env = "dev";
    private String nid = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView != null) {
            adapter = new NodeAdapter(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(adapter);
        }

        String sn = DeviceUtils.getSerialNumber(this);
        node = new NodeInfo().did(sn).type(type).nid(nid).env(env).ttl(30);
        nearby = NearbyManager.create(Vertx.vertx(), LoggerFactory.create("app"));
    }

    public void onButtonStart(View view) {
        if (! started) {
            new Thread(this::start).start();
        }
    }

    private void start() {
        String ip = NetworkUtils.getHostAddress(this);
        node.ip(ip);

        runOnUiThread(() -> setTitle("ip: " + ip));

        nearby.start(NetworkUtils.getBroadcastAddress(ip), 5354)
                .onComplete(ar -> {
                    if (ar.succeeded()) {
                        Log.d(TAG, "start succeeded!");
                        nearby.startDiscovery(env, nid, this);
                        nearby.register(node);
                        started = true;
                    } else {
                        Log.d(TAG, "start failed: " + ar.cause().toString());
                        started = false;
                    }
                });
    }

    public void onButtonStop(View view) {
        if (started) {
            nearby.stop()
                    .onComplete(ar -> {
                        if (ar.succeeded()) {
                            Log.d(TAG, "stop succeeded!");
                            started = false;
                        } else {
                            Log.d(TAG, "stop failed: " + ar.cause().toString());
                            started = false;
                        }
                    });
        }
    }

    @Override
    public void onFound(NodeInfo nodeInfo) {
        adapter.nodes().add(nodeInfo);
        runOnUiThread(() -> adapter.notifyDataSetChanged());
    }

    @Override
    public void onLost(NodeInfo nodeInfo) {
        adapter.nodes().remove(nodeInfo);
        runOnUiThread(() -> adapter.notifyDataSetChanged());
    }
}