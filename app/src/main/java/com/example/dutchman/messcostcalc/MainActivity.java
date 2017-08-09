package com.example.dutchman.messcostcalc;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dutchman.messcostcalc.constants.Constant;
import com.example.dutchman.messcostcalc.constants.MySharedPref;
import com.example.dutchman.messcostcalc.db.DBHandler;
import com.example.dutchman.messcostcalc.fragments.AboutFragment;
import com.example.dutchman.messcostcalc.fragments.AddMealFragment;
import com.example.dutchman.messcostcalc.fragments.BazarFragment;
import com.example.dutchman.messcostcalc.fragments.CostFragment;
import com.example.dutchman.messcostcalc.fragments.HomeFragment;
import com.example.dutchman.messcostcalc.fragments.MealCreditFragment;
import com.example.dutchman.messcostcalc.fragments.MealHistoryFragment;
import com.example.dutchman.messcostcalc.fragments.MemberFragment;
import com.example.dutchman.messcostcalc.fragments.RentCreditFragment;
import com.example.dutchman.messcostcalc.fragments.RentHistoryFragment;
import com.example.dutchman.messcostcalc.fragments.SettingsFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager;
    private HomeFragment homeFragment;
    private FloatingActionButton fab;

    private boolean isMembersFrag = true,isHomeFrag = false;

    private DBHandler handler;
    private Context context;

    private String DBNAME = "messCostInfo";

    public static boolean backFromMemberEditActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        context = this;


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_add_white));
//        if (fab != null) {
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // Click action
//
//
//                    if(isMembersFrag){
//                        isMembersFrag = false;
//                        isHomeFrag = true;
//
//                        //android.R.drawable.ic_menu_close_clear_cancel
//
//
//                        fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_close_white));
//
//                        BazarFragment bazarFragment = new BazarFragment();
//                        bazarFragment.setContext(context);
//
//                        manager = getSupportFragmentManager();
//                        manager.beginTransaction().replace(R.id.rlContent,bazarFragment,bazarFragment.getTag())
//                                .commit();
//                        //fab.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
//
//
//
//                    } else if(isHomeFrag){
//
//                        isMembersFrag = true;
//                        isHomeFrag = false;
//
//                        fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_add_white));
//
//                        homeFragment = new HomeFragment();
//                        homeFragment.setContext(context);
//
//                        manager = getSupportFragmentManager();
//                        manager.beginTransaction().replace(R.id.rlContent,homeFragment,homeFragment.getTag()).commit();
//                        //fab.setImageResource(android.R.drawable.ic_input_add);
//                    }
//
//                }
//            });
//        }


//        if(!backFromMemberEditActivity) {
//            setNavItem(R.id.nav_home);
//        } else{
//            backFromMemberEditActivity = false;
//            setNavItem(R.id.nav_addMember);
//        }

        setMyFragment();
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }

        Log.d("MainActivity", "onBackPressed: Home back");

        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.action_export){

            exportDB(DBNAME);

        } else if(id == R.id.action_import){

//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//            Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +
//                    File.separator + "mccdb"+
//                    File.separator);
//            intent.setDataAndType(uri,"*/*");
//            //startActivity(Intent.createChooser(intent, "Open folder"));
//
//            //intent.setType("*/*");
//            startActivityForResult(Intent.createChooser(intent, "Import database"), 7);

            importDB(DBNAME);

        } else if (id == R.id.action_settings) {

            SettingsFragment settingsFragment = new SettingsFragment();

            settingsFragment.setContext(context);

            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent,settingsFragment,settingsFragment.getTag()).commit();

            return true;
        } else if(id == R.id.action_about){

            AboutFragment aboutFragment = new AboutFragment();

            aboutFragment.setContext(context);

            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent,aboutFragment,aboutFragment.getTag()).commit();

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Toast.makeText(MainActivity.this,"Under construction",Toast.LENGTH_SHORT).show();

//        fab.setVisibility(View.VISIBLE);

        setNavItem(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    private void setMyFragment(){

        String fragmentName = MySharedPref.get(this);

        if(fragmentName.equals(Constant.Class.MEMBER)){
            MySharedPref.save(this,Constant.Class.HOME);
            setNavItem(R.id.nav_addMember);

        } else if(fragmentName.equals(Constant.Class.BAZAR)){
            MySharedPref.save(this,Constant.Class.HOME);
            setNavItem(R.id.nav_bazar);

        } else if(fragmentName.equals(Constant.Class.MEAL_CREDIT_DEBIT)){

            MySharedPref.save(this, Constant.Class.HOME);
            setNavItem(R.id.nav_meal_credit);

        } else if(fragmentName.equals(Constant.Class.RENT_CREDIT_DEBIT)) {

            MySharedPref.save(this, Constant.Class.HOME);
            setNavItem(R.id.nav_rent_credit);

        }else{

            setNavItem(R.id.nav_home);
        }


    }

    private void setNavItem(int id){

        if (id == R.id.nav_home) {

            homeFragment = new HomeFragment();
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent,homeFragment,homeFragment.getTag()).commit();
            getSupportActionBar().setSubtitle("Home");


        } else if(id == R.id.nav_addMember){

            MemberFragment memberFragment = new MemberFragment();
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent,memberFragment,memberFragment.getTag())
                    .commit();

            getSupportActionBar().setSubtitle("Members");

        } else if (id == R.id.nav_add_meal) {


            AddMealFragment addMealFragment = new AddMealFragment();
            manager = getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.rlContent, addMealFragment, addMealFragment.getTag()).commit();

            getSupportActionBar().setSubtitle("Add Meal");


        } else if(id == R.id.nav_bazar){

            BazarFragment bazarFragment = new BazarFragment();
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent, bazarFragment, bazarFragment.getTag()).commit();
            getSupportActionBar().setSubtitle("Bazar");

        }else if (id == R.id.nav_meal_credit) {
            MealCreditFragment mealCreditFragment = new MealCreditFragment();
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent, mealCreditFragment, mealCreditFragment.getTag()).commit();
            getSupportActionBar().setSubtitle("Add Credit");

        } else if(id == R.id.nav_meal_history){


            MealHistoryFragment mealHistoryFragment = new MealHistoryFragment();

            manager = getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.rlContent, mealHistoryFragment, mealHistoryFragment.getTag()).commit();

            getSupportActionBar().setSubtitle("Meal History");


        } else if (id == R.id.nav_rent_credit) {

            RentCreditFragment rentCreditFragment = new RentCreditFragment();

            manager = getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.rlContent, rentCreditFragment, rentCreditFragment.getTag()).commit();

            getSupportActionBar().setSubtitle("Add Credit");


//        } else if (id == R.id.nav_rent_per_debit) {
//
//            DebitFragment debitFragment = new DebitFragment();
//            debitFragment.setDest(context, "rent");
//
//            manager = getSupportFragmentManager();
//
//            manager.beginTransaction().replace(R.id.rlContent, debitFragment, debitFragment.getTag()).commit();
//
//            getSupportActionBar().setSubtitle("Debit");

        }
        else if (id == R.id.nav_rent_cost) {

            CostFragment costFragment = new CostFragment();
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent, costFragment, costFragment.getTag()).commit();

            getSupportActionBar().setSubtitle("Rent Cost");

        } else if(id == R.id.nav_rent_history){


            RentHistoryFragment rentHistoryFragment = new RentHistoryFragment();
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent, rentHistoryFragment, rentHistoryFragment.getTag()).commit();

            getSupportActionBar().setSubtitle("Rent History");

        }

    }


    private void exportDB(String db_name){

        File sd = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +
                File.separator + "mccdb"+
                File.separator );

        boolean success = true;
        if (!sd.exists()) {
            success = sd.mkdir();
        }
        if (success) {

            File data = Environment.getDataDirectory();
            FileChannel source=null;
            FileChannel destination=null;
            String currentDBPath = "/data/"+ context.getPackageName() +"/databases/"+db_name;
            String backupDBPath = db_name;
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(sd, backupDBPath);
            try {
                source = new FileInputStream(currentDB).getChannel();
                destination = new FileOutputStream(backupDB).getChannel();
                destination.transferFrom(source, 0, source.size());
                source.close();
                destination.close();
                Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();
            } catch(IOException e) {
                Log.d("MainActivity", "exportDB: Error");
            }
        }
    }



    private void importDB(String db_name){
        File sd = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +
                File.separator + "mccdb"+
                File.separator );
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String backupDBPath = "/data/"+ context.getPackageName() +"/databases/"+db_name;
        String currentDBPath = db_name;
        File currentDB = new File(sd, currentDBPath);
        File backupDB = new File(data, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
