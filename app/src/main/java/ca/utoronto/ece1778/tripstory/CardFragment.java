package ca.utoronto.ece1778.tripstory;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;



public class CardFragment extends Fragment {

    ArrayList<StoryModel> listitems = new ArrayList<>();
    RecyclerView MyRecyclerView;
    String Themes[] = {"Underwater Adventure", "Trip to the Zoo!"};
    String Images[] = {"scuba","zoo"};
    public ArrayList Stories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeList();
        getActivity().setTitle("Rebus Story Library");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(true);

        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listitems.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new MyAdapter(listitems));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<StoryModel> list;

        public MyAdapter(ArrayList<StoryModel> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_items, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            holder.titleTextView.setText(list.get(position).getCardName());
            holder.coverImageView.setImageResource(list.get(position).getImageResourceId());
            holder.coverImageView.setTag(list.get(position).getImageResourceId());
            holder.likeImageView.setTag(R.mipmap.ic_favorite_black_24dp);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public ImageView coverImageView;
        public ImageView likeImageView;
        public ImageView shareImageView;

        public MyViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            coverImageView = (ImageView) v.findViewById(R.id.coverImageView);
            likeImageView = (ImageView) v.findViewById(R.id.likeImageView);
            shareImageView = (ImageView) v.findViewById(R.id.shareImageView);

            coverImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //Toast.makeText(getActivity(),getLayoutPosition(),Toast.LENGTH_SHORT).show();
                    System.out.println("Position = " + getLayoutPosition());
                    int position = getLayoutPosition();

                    Intent intent = new Intent(getContext(), FlipperActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("Stories", Stories);
                    startActivity(intent);
                }
            });


        likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int id = (int)likeImageView.getTag();
                if( id == R.mipmap.ic_favorite_black_24dp){

                    likeImageView.setTag(R.mipmap.ic_favorite_black_24dp);
                    likeImageView.setImageResource(R.mipmap.ic_favorite_black_24dp);

                    Toast.makeText(getActivity(),titleTextView.getText()+" added to favourites",Toast.LENGTH_SHORT).show();

                }else{

                    likeImageView.setTag(R.mipmap.ic_favorite_black_24dp);
                    likeImageView.setImageResource(R.mipmap.ic_favorite_black_24dp);
                    Toast.makeText(getActivity(),titleTextView.getText()+" removed from favourites",Toast.LENGTH_SHORT).show();


                }

            }
        });

        shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(coverImageView.getId())
                        + '/' + "drawable" + '/' + getResources().getResourceEntryName((int)coverImageView.getTag()));

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
                shareIntent.setType("image/jpeg");
                //startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
                //startActivity(Intent.createChooser(shareIntent, getResources().getText(1)));

            }
        });

    }
}

    public void initializeList() {
        listitems.clear();

        Stories = existFiles();

        for(int i=0;i<Stories.size();i++){
            StoryModel item = new StoryModel();
            // It is going to be only underwater adventure for the time being ...
            item.setCardName("Underwater Adventure " + i+1);
            item.setImageResourceId(getResources().getIdentifier("scuba", "drawable", "ca.utoronto.ece1778.tripstory"));

            item.setIsfav(0);
            item.setIsturned(0);
            listitems.add(item);
        }
    }

    public static String getFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
    }

    public ArrayList existFiles() {

        // Create a list with all the items in the DCIM folder and then capture the ones
        // that have the name Story in the filename to display later
        String ExternalStorageDirectoryPath = Environment.getExternalStoragePublicDirectory("/DCIM").getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath + "/Camera";
        File targetDirector = new File(targetPath);
        File[] files = targetDirector.listFiles();
        ArrayList stories = new ArrayList<String>();

        for (File file : files) {
            String filename = getFileName(file.getAbsolutePath());
            if (filename.indexOf("_Story") > 0) {
                stories.add(file.getAbsolutePath());
                System.out.println("List of Stories: " + file.getAbsolutePath());
            }
        }

        return stories;

    }

}