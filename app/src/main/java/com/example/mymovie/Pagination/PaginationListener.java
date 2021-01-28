package com.example.mymovie.Pagination;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationListener extends RecyclerView.OnScrollListener {

    public static final int PAGE_START = 1;


    @NonNull
    private LinearLayoutManager layoutManager;              //todo LinearLayoutManager который управляет ресайлером!!!

    public PaginationListener(@NonNull LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // то что я вижу на екране клво
        int visibleItemCount = layoutManager.getChildCount();   //todo текущее количество дочерних представлений,
                                                                // прикрепленных к родительскому RecyclerView.
        //позиция первого(верхнего) видимого елмета в ресайлере
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
         //todo Позиция адаптера первого видимого элемента или
         // {@link RecyclerView # NO_POSITION}, если нет видимых элементов.
         //обще количесто елементо в ресайлере(видимых и нет)
        int totalItemCount = layoutManager.getItemCount();   //todo Количество элементов в привязанном адаптере

      //todo если НЕ  загрузка и НЕ последняя страница
        if (!isLoading()) {
                                          //todo++
        //todo      сумма текущих +    первый видимый???     >= кол.всех
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    public abstract boolean isLoading();


}
