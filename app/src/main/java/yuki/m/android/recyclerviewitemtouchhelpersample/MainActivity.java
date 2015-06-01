package yuki.m.android.recyclerviewitemtouchhelpersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
  private LinkedList<String> datasource
      = new LinkedList<>(Arrays.asList("data1", "data2", "data3", "data4", "data5", "data6", "data7", "data8", "data9"));
  private RecyclerViewAdapter adapter;


  static class MainViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView;
    private TextView summaryTextView;

    public MainViewHolder(View itemView) {
      super(itemView);
      titleTextView = (TextView) itemView.findViewById(android.R.id.text1);
      summaryTextView = (TextView) itemView.findViewById(android.R.id.text2);
    }

    public void setText(String title, String summary) {
      titleTextView.setText(title.toUpperCase());
      summaryTextView.setText("-" + summary);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    recyclerView.setHasFixedSize(false);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new RecyclerViewAdapter();
    recyclerView.setAdapter(adapter);
    ItemTouchHelper itemDecor = new ItemTouchHelper(
        new SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT) {
          @Override
          public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
            final int fromPos = viewHolder.getAdapterPosition();
            final int toPos = target.getAdapterPosition();
            adapter.notifyItemMoved(fromPos, toPos);
            return true;
          }

          @Override
          public void onSwiped(ViewHolder viewHolder, int direction) {
            final int fromPos = viewHolder.getAdapterPosition();
            datasource.remove(fromPos);
            adapter.notifyItemRemoved(fromPos);
          }
        });
    itemDecor.attachToRecyclerView(recyclerView);
  }

  private class RecyclerViewAdapter
      extends RecyclerView.Adapter<MainViewHolder> {

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext())
          .inflate(android.R.layout.simple_list_item_2, parent, false);
      return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainViewHolder view, int position) {
      view.setText(datasource.get(position), datasource.get(position));
    }

    @Override
    public int getItemCount() {
      return datasource.size();
    }
  }
}
