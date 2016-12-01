package sriyaan.ac;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dipesh on 18-Jul-16.
 */
public class CustomerCollectionAddAdapter extends RecyclerView.Adapter<CustomerCollectionAddAdapter.ViewHolder> {

    private List<Settergetter> arrayListimage;
    private Context mContext;

    public CustomerCollectionAddAdapter(Context mContext, List<Settergetter> arrayListimage) {
        this.mContext = mContext;
        this.arrayListimage = arrayListimage;
    }

    @Override
    public CustomerCollectionAddAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.collectiondetailsadd,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerCollectionAddAdapter.ViewHolder holder, int position) {
        Settergetter movie = arrayListimage.get(position);
        holder.customer_name.setText(movie.getMachine_name());
//        holder.genre.setText(movie.getGenre());
//        holder.year.setText(movie.getYear());

//        Picasso.with(mContext).load(Constant_url.Image_url+ arrayListimage.get(position).
//                getPath()).resize(180,80).error(R.drawable.event).placeholder(R.drawable.noimage).into(holder.image);
//
////        feedListRowHolder.title.setText(Html.fromHtml(feedItem.getTitle()));

    }

    @Override
    public int getItemCount() {
        return (null != arrayListimage ? arrayListimage.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView customer_name;
        public ViewHolder(View itemView) {
            super(itemView);
//            this.image = (ImageView) itemView.findViewById(R.id.thumbnail);
//            this.title = (TextView) itemView.findViewById(R.id.ac_name_edit);
            this.customer_name = (TextView) itemView.findViewById(R.id.amount1);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mContext.startActivity(new Intent(mContext, MainActivity.class));
//            String title1 = title.getText().toString();
//            Intent intent = new Intent(mContext,EventDetails.class);
//            intent.putExtra("title",title1);
//            mContext.startActivity(intent);
        }
    }
}
