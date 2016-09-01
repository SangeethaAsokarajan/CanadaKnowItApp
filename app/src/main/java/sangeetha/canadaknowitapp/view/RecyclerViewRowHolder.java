package sangeetha.canadaknowitapp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import sangeetha.canadaknowitapp.R;

/**
 * Created by Sangeetha on 8/30/2016.
 */
public class RecyclerViewRowHolder extends RecyclerView.ViewHolder {

    public ImageView thumbnail;
    public TextView title;
    public TextView description;

    public RecyclerViewRowHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        this.title = (TextView) view.findViewById(R.id.title);
        this.description = (TextView) view.findViewById(R.id.description);
    }
}
