package org.izv.flora;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.view.AddFloraActivity;
import org.izv.flora.view.EditFlora;

public class FloraViewHolder extends RecyclerView.ViewHolder {

    public Flora flora;
    public TextView tvNombreFlora;
    public ImageView ivFlora;

    public FloraViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNombreFlora = itemView.findViewById(R.id.textView2);
        ivFlora= itemView.findViewById(R.id.ivFlora);


        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(itemView.getContext(), EditFlora.class);
            intent.putExtra("flora", flora);
            itemView.getContext().startActivity(intent);
        });
    }

}
