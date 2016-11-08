package com.locals;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    RelativeLayout home, locali, eventi, servizi;
    FrameLayout mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        home = (RelativeLayout) findViewById(R.id.layoutHome);
        locali = (RelativeLayout) findViewById(R.id.layoutLocali);
        eventi = (RelativeLayout) findViewById(R.id.layoutEventi);
        servizi = (RelativeLayout) findViewById(R.id.layoutServizi);
        mainView = (FrameLayout) findViewById(R.id.container);

        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        setUpDrawerToggle();

        Fragment vFragment = new Home();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, vFragment);
        ft.commit();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment vFragment = new Home();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, vFragment);
                ft.commit();
                mDrawerLayout.closeDrawers();
            }
        });

        locali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*                Intent vIntent = new Intent(MainActivity.this, SchermataLocali.class);
                startActivity(vIntent);*/
                Fragment vFragment = new SchermataLocali();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, vFragment);
                ft.commit();
                mDrawerLayout.closeDrawers();
            }
        });
    }


    private void setUpDrawerToggle() {
        ActionBar actionBar = getSupportActionBar();//recupero la mia ActionBar support oppure no

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //rendo abilitato il bottone home della actionBar

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.app_name,
                R.string.app_name
        ) {//istanzio il DrawerToggle
            @Override
            public void onDrawerClosed(View drawerView) {
                supportInvalidateOptionsMenu(); // cito chi lo spiega meglio di me: invalidateOptionsMenu() is used to say Android, that contents of menu have changed, and menu should be redrawn. For example, you click a button which adds another menu item at runtime, or hides menu items group
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });//serve per sincronizzare il menù
        mDrawerLayout.setDrawerListener(mDrawerToggle);//assegno al drawerlayout il suo drawertoggle
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //l'override di questo metodo serve per intercettare il click sulla actionbar in modo da passarlo al drawertoggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
