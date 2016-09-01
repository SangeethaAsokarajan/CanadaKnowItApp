package sangeetha.canadaknowitapp.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import sangeetha.canadaknowitapp.DataModel.DataCanada;
import sangeetha.canadaknowitapp.R;
import sangeetha.canadaknowitapp.view.RecyclerViewRowHolder;
/**
 * Created by Sangeetha on 8/30/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewRowHolder> {

    private List<DataCanada> itemList;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<DataCanada> infoItemList) {
        this.itemList = infoItemList;
        this.mContext = context;
    }
    @Override
    public RecyclerViewRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_list_row, viewGroup, false);
        RecyclerViewRowHolder rowHolder = new RecyclerViewRowHolder(v);
        return rowHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerViewRowHolder rowHolder, int i) {
        DataCanada rowItem = itemList.get(i);
        rowHolder.thumbnail.setImageResource(R.drawable.star);
        rowHolder.title.setText(rowItem.getTitle());
        rowHolder.description.setText(rowItem.getDescription());
    }
    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }
}

