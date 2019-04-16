package com.example.cc.code_container;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.cc.code_container.greendao.CodeDao;
import com.gjiazhe.wavesidebar.WaveSideBar;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "user";
    private static final String DETAIL_MODE = "MODE_OF_ACTIVITY";
    private PinyinComparator pinyinComparator;
    private BoomMenuButton boomMenuButton;
    private CodeListAdapter mAdapter;
    private static String username;
    private SearchView searchView;
    private WaveSideBar waveSideBar;
    LinearLayoutManager manager;
    private static int index_text = 0;
    private static int index_image = 0;
    private static int index_color = 0;
    //private static int[] color = new int[]{R.color.Brown, R.color.purple, R.color.Lightblue, R.color.green};
    private static int[] image_resource = new int[]{R.drawable.add, R.drawable.setting, R.drawable.version, R.drawable.logout};
    private static String[] text = new String[]{"新增", "设置", "版本", "登出"};

    @BindView(R.id.items_list)
    RecyclerView mCodeRecyclerView;

   // @BindView(R.id.add_button)
    //FloatingActionButton mAddBt;

    //@BindView(R.id.design_navigation_view)
   // NavigationView designNavigationView;

    //@BindView(R.id.drawer_layout)
   // DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /*
        designNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.setting:
                        Intent set = new Intent(MainActivity.this, setting.class);
                        set.putExtra(TAG, username);
                        startActivity(set);
                        break;

                    case R.id.version:
                        startActivity(new Intent(MainActivity.this, version.class));
                        break;

                    default:
                        drawerLayout.closeDrawer(designNavigationView);
                }
                return false;
            }
        }); */
        updateUI();

        manager = new LinearLayoutManager(this);
        mCodeRecyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        mCodeRecyclerView.setLayoutManager(manager);

        waveSideBar = (WaveSideBar) findViewById(R.id.side_bar);
        // ButterKnife没起作用，没有绑定控件，错误跳转到activitythread.java
        waveSideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                int position = mAdapter.getPositionForSection(index.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        boomMenuButton = (BoomMenuButton) findViewById(R.id.bmb);
        for (int i=0; i<boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder().listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    switch (index) {
                        case 0:
                            Intent data = new Intent(MainActivity.this, DetailActivity.class);
                            data.putExtra(TAG, username);
                            data.putExtra(DETAIL_MODE, "add");
                            startActivity(data);
                            break;
                        case 1:
                            Intent set = new Intent(MainActivity.this, setting.class);
                            set.putExtra(TAG, username);
                            startActivity(set);
                            break;
                        case 2:
                            startActivity(new Intent(MainActivity.this, version.class));
                            break;
                        case 3:
                            Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            //logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(logoutIntent);
                            break;
                        default:
                            boomMenuButton.reboom();
                    }
                    //Toast.makeText(MainActivity.this, "Click" + index, Toast.LENGTH_SHORT).show();;
                }
            }).normalImageRes(getImage()).normalText(getText()).normalColorRes(R.color.white);
            boomMenuButton.addBuilder(builder);
        }
    }

    //此处，activity中的写法和fragment中的解法不同
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        //API26以后的写法, 但menu里的item要用"android.widget.SearchView"，而不是"android.support.v7.widget.SearchView"
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                updateUI();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setSearchView(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /*
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.search:
                    //private String searchEt =
                    return true;
                case android.R.id.home:
                    updateUI();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    */
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);

    }
/*
    @OnClick(R.id.add_button)
    public void onViewClicked() {
        Intent data = new Intent(MainActivity.this, DetailActivity.class);
        data.putExtra(TAG, username);
        data.putExtra(DETAIL_MODE, "add");
        startActivity(data);
    } */

    public static String getText() {
        if (index_text >= text.length) {
            index_text = 0;
        }
        return text[index_text++];
    }

    public static int getImage() {
        if (index_text >= image_resource.length) {
            index_image = 0;
        }
        return image_resource[index_image++];
    }

   /* public static int getColor() {
        if (index_color >= color.length) {
            index_color = 0;
        }
        return color[index_color++];
    } */
    public void updateUI() {
        if (getIntent().getSerializableExtra(TAG) == null) {
            CodeDao itemdao = MyApplication.getInstance().getDaoSession().getCodeDao();
            List<Code> codeList = itemdao.queryBuilder().where(CodeDao.Properties.MMainName.eq(username)).list();
            pinyinComparator = new PinyinComparator();
            Collections.sort(codeList, pinyinComparator);
            if (mAdapter == null) {
                mAdapter = new CodeListAdapter(codeList, this);
                mCodeRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setCodes(codeList);
                mAdapter.notifyDataSetChanged();
            }
        } else {
            username = getIntent().getSerializableExtra(TAG).toString();
            CodeDao itemdao = MyApplication.getInstance().getDaoSession().getCodeDao();
            List<Code> codeList = itemdao.queryBuilder().where(CodeDao.Properties.MMainName.eq(username)).list();
            pinyinComparator = new PinyinComparator();
            Collections.sort(codeList, pinyinComparator);
            if (mAdapter == null) {
                mAdapter = new CodeListAdapter(codeList, this);
                mCodeRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setCodes(codeList);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setSearchView(String query) {
        CodeDao codeDao = MyApplication.getInstance().getDaoSession().getCodeDao();
        List<Code> searchList = codeDao.queryBuilder().where(CodeDao.Properties.MTitle.like("%" + query + "%")).list();
        mAdapter = new CodeListAdapter(searchList, this);
        mCodeRecyclerView.setAdapter(mAdapter);
    }

}
