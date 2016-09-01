package sangeetha.canadaknowitapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import sangeetha.canadaknowitapp.dataModel.DataCanada;
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_list_row, viewGroup, false);
        RecyclerViewRowHolder rowHolder = new RecyclerViewRowHolder(view);
        return rowHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewRowHolder rowHolder, int i) {
        DataCanada rowItem = itemList.get(i);
        rowHolder.title.setText(rowItem.getTitle());
        rowHolder.description.setText(rowItem.getDescription());
        rowHolder.thumbnail.setImageBitmap(null);

        //loading image from url path
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        if (rowItem.getThumbnail() != null && !rowItem.getThumbnail().equals("")) {
            final File image = DiskCacheUtils.findInCache(rowItem.getThumbnail(), imageLoader.getDiskCache());
            if (image != null && image.exists()) {
                Picasso.with(mContext).load(image).fit().centerCrop().into(rowHolder.thumbnail);
            } else {
                imageLoader.loadImage(rowItem.getThumbnail(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                        //todo: handle initial state
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String s, View view, final Bitmap bitmap) {
                        //todo: handle initial state
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {

                    }
                });
            }
        } else {
            rowHolder.thumbnail.setImageBitmap(null);
        }

    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }
}

