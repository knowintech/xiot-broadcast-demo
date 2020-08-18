package com.kowin.iot.udp.broadcastdemo;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knowin.iot.logger.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import cn.geekcity.xiot.logger.XLogger;
import cn.geekcity.xiot.nearby.vertx.typedef.node.NodeInfo;

public class NodeAdapter extends RecyclerView.Adapter<NodeViewHolder> {

    private static final String TAG = "NodeAdapter";
    private final XLogger logger = LoggerFactory.create("app");
    private List<NodeInfo> list = new ArrayList<>();
    private final int mBackground;
    private final TypedValue mTypedValue = new TypedValue();
    private Context context;

    public NodeAdapter(Context context) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        this.context = context;
    }

    @NonNull
    @Override
    public NodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_node, parent, false);
        v.setBackgroundResource(mBackground);
        return new NodeViewHolder(context, v);
    }

    @Override
    public void onBindViewHolder(@NonNull final NodeViewHolder holder, int position) {
        holder.onBind(position, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<NodeInfo> nodes() {
        return list;
    }

    public void nodes(List<NodeInfo> nodes) {
        logger.d(TAG, "nodes: " + nodes.size());

        this.list = nodes;
    }

    public void remove(int position) {
        this.list.remove(position);
    }
}
