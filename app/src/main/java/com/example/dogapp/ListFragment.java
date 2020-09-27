package com.example.dogapp;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.service.DogsApiService;
import com.example.dogapp.supporter.DogListAdapter;
import com.example.dogapp.supporter.MyViewModel;

public class ListFragment extends Fragment {
    // supporters
    private DogListAdapter adapter;
    private MyViewModel viewModel;
    private DogsApiService apiService;
    private GridLayoutManager layoutManager;

    // components
    private RecyclerView rvDogBreeds;

    // values
    private final int SPAN_NUMBER = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();

        mapComponents();

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.initialDogBreedData();
        viewModel.getAllDogBreedLiveData().observe(getViewLifecycleOwner(), dogBreeds -> {
            adapter = new DogListAdapter(getContext(), dogBreeds);
            rvDogBreeds.setAdapter(adapter);
        });
        layoutManager = new GridLayoutManager(getContext(), SPAN_NUMBER);
        rvDogBreeds.setLayoutManager(layoutManager);
        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.black_gray));

//            @Override
//            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//                System.out.println("onChildDrawOver");
//            }
//
//            @Override
//            public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
//                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
//                System.out.println("onMoved");
//            }
//
//            @Override
//            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
//                super.onSelectedChanged(viewHolder, actionState);
//                System.out.println("onSelectedChanged");
//            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            ///


            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                System.out.println("ON MOVE");
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.showDraftAtPosition(viewHolder.getAdapterPosition());
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                System.out.println("onChildDraw");
                View itemView = viewHolder.itemView;

                if (dX > 0) {
                    System.out.println("dX >>>>>>>>>>>>>> 0");
                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                } else if (dX < 0) {
                    System.out.println("dX <<<<<<<<<<<<<< 0");
                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else {
                    System.out.println("dX =============== 0");
                    background.setBounds(0, 0, 0, 0);
                }

//                background.draw(c);
                View v = getLayoutInflater().inflate(R.layout.draft_item, null);
                v.draw(c);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(rvDogBreeds);

        initialEvents();
    }



    private void initialEvents() {
        getView().setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK )
            {
                adapter.closeDraft();
                return true;
            }
            return false;
        });
    }

    private void mapComponents() {
        rvDogBreeds = getView().findViewById(R.id.rv_dog_list);
    }
}