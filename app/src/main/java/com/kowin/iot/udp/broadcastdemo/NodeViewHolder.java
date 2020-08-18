package com.kowin.iot.udp.broadcastdemo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knowin.iot.logger.LoggerFactory;

import cn.geekcity.xiot.logger.XLogger;
import cn.geekcity.xiot.nearby.vertx.typedef.node.NodeInfo;

public class NodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "NodeViewHolder";
    private final XLogger logger = LoggerFactory.create("app");
    private Context context;
    private NodeInfo node;
    private TextView textViewDID;
    private TextView textViewIP;
    private int position;

    public NodeViewHolder(Context context, @NonNull View v) {
        super(v);
        this.context = context;
        textViewDID = v.findViewById(R.id.textViewDID);
        textViewIP = v.findViewById(R.id.textViewIP);
        v.setOnClickListener(this);
        v.setLongClickable(true);
        v.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        logger.d(TAG, "onClick: " + node.did());
    }

    @Override
    public boolean onLongClick(View v) {
        logger.d(TAG, "onLongClick: " + node.did());
        return true;
    }

    public void onBind(int position, NodeInfo info) {
        this.position = position;
        this.node = info;
        textViewDID.setText(info.did());
        textViewIP.setText(info.ip());
    }
}
