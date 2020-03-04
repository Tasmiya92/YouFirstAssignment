package com.example.youfirstassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youfirstassignment.sqlite.DataModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//RecyclerView Adapter for setting data
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
	List<DataModel> dashboardDetails = new ArrayList<>();
	private Context mContext;

    public RecyclerAdapter(Context context, List<DataModel> mdashboardDetails) {
		mContext = context;
		dashboardDetails = mdashboardDetails;
	}


		@NonNull
		@Override
		public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
			View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
			MyViewHolder viewHolder = new MyViewHolder(view);
			return viewHolder;
		}


	@Override
		public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder myViewHolder, int i) {
			DataModel item = dashboardDetails.get(i);
			myViewHolder.tvDate.setText(item.getDate());
			myViewHolder.tvPointsEarned.setText(item.getPointsEarned());
			myViewHolder.tvRedeemedPoints.setText(item.getPointsReedemed());
			myViewHolder.tvSlNo.setText(String.valueOf(i+1));

		}

		@Override
		public int getItemCount() {
			return dashboardDetails.size();
		}

public class MyViewHolder extends RecyclerView.ViewHolder {
	TextView tvDate,tvPointsEarned,tvRedeemedPoints,tvSlNo;
	public MyViewHolder(@NonNull View itemView) {
		super(itemView);
		tvDate = itemView.findViewById(R.id.tvDate);
		tvSlNo = itemView.findViewById(R.id.tvNo);
		tvPointsEarned = itemView.findViewById(R.id.tvPointsEarned);
		tvRedeemedPoints = itemView.findViewById(R.id.tvPointsRedeemed);

	}
}
}

