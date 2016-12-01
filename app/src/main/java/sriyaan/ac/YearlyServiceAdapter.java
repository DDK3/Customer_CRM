package sriyaan.ac;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dipesh on 26-Jul-16.
 */
public class YearlyServiceAdapter extends RecyclerView.Adapter<YearlyServiceAdapter.ViewHolder> {

     List<Settergetter> arrayListservice;
     Context mContext;

    public YearlyServiceAdapter(Context mContext, List<Settergetter> arrayListservice) {
        this.mContext = mContext;
        this.arrayListservice = arrayListservice;
    }

    @Override
    public YearlyServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.schedule_list,parent,false);
        //  YearlyService.schedule_id=arrayListservice.get(viewType).getId();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YearlyServiceAdapter.ViewHolder holder, int position) {
        Settergetter list = arrayListservice.get(position);
//        holder.amount.setText(list.getAmount());
        holder.no.setText(list.getService_schedule());
        holder.date.setText(list.getAdd_date());
        String status = list.getStatus();


        if (status.equals("0")||status.equals(""))
            {
                holder.checkbox.setChecked(false);

            }
        else
            {
                holder.checkbox.setChecked(true);
                holder.checkbox.setEnabled(false);
            }
//
//        sr_no=position;
//
//        holder.no.setTag(position);
//        holder.no.getTag(position);
//        holder.no.setText(String.valueOf(sr_no+1));
//

    }

    @Override
    public int getItemCount() {
        return arrayListservice.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView no, date;
        public CheckBox checkbox;
        public String check;
        public ViewHolder(View itemView) {
            super(itemView);
            //            this.image = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.no = (TextView) itemView.findViewById(R.id.srno);
            this.checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            this.date = (TextView) itemView.findViewById(R.id.date);




            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    if (isChecked) {
                        // it is check
                        YearlyService.status_check = "1";
                         int position=getAdapterPosition();
                        YearlyService.schedule_id=arrayListservice.get(position).getId();
                    } else {
                        // it is unchecked
                        YearlyService.status_check ="0";
                    }
                }
            });




        }


    }
}
