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
public class CustomerCollectionDetailsAdapter extends RecyclerView.Adapter<CustomerCollectionDetailsAdapter.ViewHolder>{

    private List<Settergetter> arrayListimage;
    private Context mContext;


    int sr_no;
    public CustomerCollectionDetailsAdapter(Context mContext, List<Settergetter> arrayListimage) {
        this.mContext = mContext;
        this.arrayListimage = arrayListimage;
    }

    @Override
    public CustomerCollectionDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.collectiondetails,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerCollectionDetailsAdapter.ViewHolder holder, int position) {
        Settergetter list = arrayListimage.get(position);
        holder.amount.setText(list.getAmount());
        holder.date.setText(list.getRemark());

        sr_no=position;

        holder.no.setTag(position);
        holder.no.getTag(position);
        holder.no.setText(String.valueOf(sr_no+1));

        String pay_type1 = list.getPay();
        if(pay_type1.equals("1")){
            holder.paytype.setText("Cheque");
        }else{
            holder.paytype.setText("Cash");
        }

        holder.chequeno.setText(list.getChequeno());

//        sr_no=  arrayListimage.get(position);
//        holder.no.setText(sr_no+1);

//        holder.genre.setText(list.getGenre());
//        holder.year.setText(list.getYear());

//        Picasso.with(mContext).load(Constant_url.Image_url+ arrayListimage.get(position).
//                getPath()).resize(180,80).error(R.drawable.event).placeholder(R.drawable.noimage).into(holder.image);
//
////        feedListRowHolder.title.setText(Html.fromHtml(feedItem.getTitle()));

    }

    @Override
    public int getItemCount() {
        return (null != arrayListimage ? arrayListimage.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView no,amount, date, paytype, chequeno;
        public ViewHolder(View itemView) {
            super(itemView);
            //            this.image = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.no = (TextView) itemView.findViewById(R.id.no);
            this.amount = (TextView) itemView.findViewById(R.id.amount);
            this.date = (TextView) itemView.findViewById(R.id.date_collection);
            this.paytype = (TextView) itemView.findViewById(R.id.paytype);
            this.chequeno = (TextView) itemView.findViewById(R.id.cheque_no);

//            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

//            mContext.startActivity(new Intent(mContext, MainActivity.class));
//            String title1 = title.getText().toString();
//            Intent intent = new Intent(mContext,EventDetails.class);
//            intent.putExtra("title",title1);
//            mContext.startActivity(intent);
        }
    }
}
