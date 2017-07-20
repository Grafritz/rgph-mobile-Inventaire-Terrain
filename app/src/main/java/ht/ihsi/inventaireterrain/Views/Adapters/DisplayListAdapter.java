package ht.ihsi.inventaireterrain.Views.Adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Models.RowDataListModel;
import ht.ihsi.inventaireterrain.R;


/**
 * Created by ajordany on 4/7/2016.
 */
public class DisplayListAdapter extends RecyclerView.Adapter<DisplayListAdapter.DetailRowViewHolder> {

    private List<RowDataListModel> targetList;
    private Context context;
    private LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;
    private OnMenuItemClickListener onMenuItemClickListener;
    private int listType;

    public DisplayListAdapter(Context context, List<RowDataListModel> targetList, int listType){
        this.targetList=targetList;
        this.context=context;
        this.mInflater=LayoutInflater.from(context);
        this.listType=listType;

    }

    @Override
    public DetailRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,null);
        DetailRowViewHolder detailRowViewHolder= new DetailRowViewHolder(view);
        return detailRowViewHolder;
    }

    @Override
    public void onBindViewHolder(DetailRowViewHolder holder, int position) {
        holder.bind(targetList.get(position));
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener){
        this.onMenuItemClickListener=listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
       return (null!= targetList ? targetList.size(): 0);
    }

    public void setFilter(List<RowDataListModel> filteredList){
        targetList=new ArrayList<RowDataListModel>();
        targetList.addAll(filteredList);
        notifyDataSetChanged();
    }

    public class DetailRowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView title;
        public TextView desc;
        public Button btnModule;
        public Button btnMenu;
        private ImageView overflowIcon;
        private RowDataListModel row;

        public DetailRowViewHolder(View view){
            super(view);

            imageView= (ImageView) view.findViewById(R.id.imageView);
            title= (TextView) view.findViewById(R.id.title);
            desc= (TextView) view.findViewById(R.id.desc);
            overflowIcon= (ImageView) view.findViewById(R.id.overflowIcon);
            overflowIcon.setOnClickListener(this);
            view.setClickable(true);
            // view.animate();
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(!row.isEmpty()){
                showContextMenu(v); // call the contextual menu
            }

        }

        public void showContextMenu(View v){
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.inflate(R.menu.recycle_view_actions);

            if(listType==Constant.LIST_LOGEMENT){
                popup.getMenu().removeItem(R.id.item_module_menu);
            }else{
                popup.getMenu().findItem(R.id.item_module_menu).setEnabled(row.isModuleMenu());

                MenuItem item=popup.getMenu().findItem(R.id.item_module_menu);
                item.setTitle(item.getTitle()+" "+getModuleName());
            }

            popup.getMenu().findItem(R.id.item_afficher).setEnabled(row.isComplete());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.item_afficher:
                            onMenuItemClickListener.onMenuItemAfficher(row);
                            break;
                        case R.id.item_modifier:
                            onMenuItemClickListener.onMenuItemModifier(row);
                            break;
                        case R.id.item_module_menu:
                            onMenuItemClickListener.onMenuItemModuleMenu(row);
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
            popup.setGravity(Gravity.RIGHT);
            popup.show();

        }

        public String getModuleName(){
            if(listType == Constant.LIST_INVENTAIRE_SDE || listType == Constant.LIST_INVENTAIRE_ALL_SDE){
                return Constant.MODULE_NAME_BATIMENT;
            }else if(listType == Constant.LIST_BATIMENT){
                return Constant.MODULE_NAME_LOGEMENT;
            }else{
                return "";
            }
        }

        private View.OnClickListener getClickModuleListener(){
            return new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemModuleClick(row);
                    }
                }
            };
        }

        private View.OnClickListener getClickMenuListener(){
            return new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemMenuClick(row);
                    }
                }
            };
        }

        public void bind(RowDataListModel row){
            this.row=row;
            int icon=getIcon();
            Picasso.with(context).load(icon).placeholder(icon)
                    .into(this.imageView);

            Picasso.with(context).load(R.drawable.dots_vertical).placeholder(R.drawable.dots_vertical)
                    .into(this.overflowIcon);

            this.title.setText(Html.fromHtml((row.getTitle())));
            this.desc.setText(Html.fromHtml(row.getDesc()));
            if(row.isEmpty()){
                overflowIcon.setVisibility(View.INVISIBLE);
            }

        }

        public int getIcon(){
            //if(listType == Constant.LIST_BATIMENT){
                return R.drawable.ic_batiment;
            //}else if(listType == Constant.LIST_INVENTAIRE_SDE || listType==Constant.LIST_INVENTAIRE_ALL_SDE){
            //    return R.drawable.ic_batiment;
            //}else if(listType == Constant.LIST_LOGEMENT){
            //    return R.drawable.ic_menage;
            //}
        }

    }

    //Interface on row click listener
    public static interface OnItemClickListener {
        public void onItemClick(RowDataListModel entity);
        public void onItemModuleClick(RowDataListModel entity);
        public void onItemMenuClick(RowDataListModel entity);
    }

    public static interface OnMenuItemClickListener{
        public void onMenuItemAfficher(RowDataListModel row);
        public void onMenuItemModifier(RowDataListModel row);
        public void onMenuItemModuleMenu(RowDataListModel row);
    }

}
