package com.tapxsouls.hackingtools.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.tapxsouls.hackingtools.R;
import com.tapxsouls.hackingtools.models.HackingTool;
import java.util.List;

public class ToolsAdapter extends BaseAdapter {
    private Context context;
    private List<HackingTool> tools;
    private LayoutInflater inflater;

    public ToolsAdapter(Context context, List<HackingTool> tools) {
        this.context = context;
        this.tools = tools;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tools.size();
    }

    @Override
    public Object getItem(int position) {
        return tools.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_tool_card, parent, false);
            holder = new ViewHolder();
            holder.iconText = convertView.findViewById(R.id.tool_icon);
            holder.nameText = convertView.findViewById(R.id.tool_name);
            holder.descriptionText = convertView.findViewById(R.id.tool_description);
            holder.statusText = convertView.findViewById(R.id.tool_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HackingTool tool = tools.get(position);
        holder.iconText.setText(tool.getIcon());
        holder.nameText.setText(tool.getName());
        holder.descriptionText.setText(tool.getDescription());
        holder.statusText.setText(tool.isInstalled() ? "Installed" : "Ready");

        return convertView;
    }

    private static class ViewHolder {
        TextView iconText;
        TextView nameText;
        TextView descriptionText;
        TextView statusText;
    }
}
