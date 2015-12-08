package android.ait.hu.shoppinglist;

import android.ait.hu.shoppinglist.adapter.ItemRecyclerAdapter;
import android.ait.hu.shoppinglist.data.Item;
import android.ait.hu.shoppinglist.touch.ItemListTouchHelper;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  public static final int REQUEST_NEW_ITEM = 101;
    public static final int REQUEST_EDIT_ITEM = 102;
    public static final String KEY_EDIT = "KEY_EDIT";
    private CoordinatorLayout layoutContent;
    private ItemRecyclerAdapter adapter;
    private DrawerLayout drawerLayout;
    private Item itemToEditHolder;
    private int itemToEditPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_main);

        List<Item> itemsList = Item.listAll(Item.class);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new ItemRecyclerAdapter(this, itemsList);

        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        layoutContent = (CoordinatorLayout) findViewById(
                R.id.layoutContent);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStart = new Intent(MainActivity.this,
                        CreateItemActivity.class);
                startActivityForResult(intentStart, REQUEST_NEW_ITEM);
            }
        });

        ItemListTouchHelper touchHelperCallback = new ItemListTouchHelper(
                adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerView);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.action_about:
                                showSnackBarMessage(getString(R.string.txt_about));
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                            case R.id.action_help:
                                showSnackBarMessage(getString(R.string.txt_help));
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                        }
                        return false;
                    }
                });
        setUpToolBar();
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.shoprite);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }
    public void showEditPlaceActivity(Item itemToEdit, int position) {
        Intent intentStart = new Intent(MainActivity.this,
                CreateItemActivity.class);
        itemToEditHolder = itemToEdit;
        itemToEditPosition = position;

        intentStart.putExtra(KEY_EDIT, itemToEdit);
        startActivityForResult(intentStart, REQUEST_EDIT_ITEM);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_NEW_ITEM) {
                    Item item = (Item) data.getSerializableExtra(
                            CreateItemActivity.KEY_ITEM);

                    adapter.addItem(item);
                    showSnackBarMessage(getString(R.string.txt_item_added));
                }else if (requestCode == REQUEST_EDIT_ITEM) {
                    Item itemTemp = (Item) data.getSerializableExtra(
                            CreateItemActivity.KEY_ITEM);

                    itemToEditHolder.setItem(itemTemp.getItem());
                    itemToEditHolder.setDescription(itemTemp.getDescription());
                    itemToEditHolder.setItemType(itemTemp.getItemType());

                    if (itemToEditPosition != -1) {
                        adapter.updateItem(itemToEditPosition, itemToEditHolder);
                        itemToEditPosition = -1;
                    }else {
                        adapter.notifyDataSetChanged();
                    }
                    showSnackBarMessage(getString(R.string.txt_item_edited));
                }
                break;
            case RESULT_CANCELED:
                showSnackBarMessage(getString(R.string.txt_item_cancelled));
                break;
        }
    }

    private void showSnackBarMessage(String message) {
        Snackbar.make(layoutContent,
                message,
                Snackbar.LENGTH_LONG
        ).setAction(R.string.action_cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //...
            }
        }).show();
    }

}
