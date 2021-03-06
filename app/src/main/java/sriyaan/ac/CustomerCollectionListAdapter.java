package sriyaan.ac;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Dipesh on 18-Jul-16.
 */
public class CustomerCollectionListAdapter extends RecyclerView.Adapter<CustomerCollectionListAdapter.ViewHolder> {

    private List<Settergetter> worldpopulationlist = null;
    private List<Settergetter> arrayListimage;
    private Context mContext;

    public CustomerCollectionListAdapter(Context mContext, List<Settergetter> worldpopulationlist) {
        this.mContext = mContext;
        this.worldpopulationlist = worldpopulationlist;
        this.arrayListimage = new ArrayList<Settergetter>();
        this.arrayListimage.addAll(worldpopulationlist);
    }

    @Override
    public CustomerCollectionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.service_schedule_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerCollectionListAdapter.ViewHolder holder, int position) {

        Settergetter list = worldpopulationlist.get(position);
        holder.customer_name.setText(list.getCust_name());
//        holder.genre.setText(movie.getGenre());
//        holder.year.setText(movie.getYear());

//        Picasso.with(mContext).load(Constant_url.Image_url+ arrayListimage.get(position).
//                getPath()).resize(180,80).error(R.drawable.event).placeholder(R.drawable.noimage).into(holder.image);
//
////        feedListRowHolder.title.setText(Html.fromHtml(feedItem.getTitle()));

    }

    @Override
    public int getItemCount() {
        return (null != worldpopulationlist ? worldpopulationlist.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView customer_name;
        public ViewHolder(View itemView) {
            super(itemView);
//            this.image = (ImageView) itemView.findViewById(R.id.thumbnail);
//            this.title = (TextView) itemView.findViewById(R.id.ac_name_edit);
            this.customer_name = (TextView) itemView.findViewById(R.id.ac_name_edit);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

//            mContext.startActivity(new Intent(mContext, CustomerCollectionDetails.class));
                    int position= getAdapterPosition();
            Settergetter list = arrayListimage.get(position);
            String title1 = list.getCust_id();
            Intent intent = new Intent(mContext,CustomerCollectionDetails.class);
            intent.putExtra("cust_id",title1);
            mContext.startActivity(intent);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arrayListimage);
        }
        else
        {
            for (Settergetter wp : arrayListimage)
            {
                if (wp.getCust_name().toLowerCase(Locale.getDefault()).contains(charText))
//                if (wp.getMachine_name().contains(charText))
                {
                    worldpopulationlist.add(wp);
                }

            }
        }
        notifyDataSetChanged();
    }
}
