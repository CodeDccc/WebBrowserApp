package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.ClipData;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;

import android.webkit.WebView;

import java.util.List;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.WebSelectable {
    String urlString = "https://google.com/";
    private static final String URL_KEY = "urlString";
    String save;
    // WebView webView;
    PageViewerFragment pageViewerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       pageViewerFragment = PageViewerFragment.newInstance(urlString);
       // pageViewerFragment = new PageViewerFragment();
      //  webView = findViewById(R.id.webView);

        if (!(getSupportFragmentManager().findFragmentById(R.id.page_control) instanceof PageControlFragment)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.page_control, PageControlFragment.newInstance(urlString))
                    .add(R.id.page_viewer, pageViewerFragment)
                    .addToBackStack(null)
                    .commit();
        }

       /* if (!(getSupportFragmentManager().findFragmentById(R.id.page_viewer) instanceof PageViewerFragment)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.page_viewer, pageViewerFragment)
                    .addToBackStack(null)
                    .commit();
        }*/


       /* if(savedInstanceState != null){
            urlString = savedInstanceState.getString(URL_KEY);
        }
        else{
            urlString = "https://google.com/";
        }*/


    }
   /* public class SharedViewModel extends ViewModel {
        private MutableLiveData<List<PageControlFragment>> mButtons = new MutableLiveData<>();

        //public SharedViewModel(){

        //}
        public void select(List list) {
            mButtons.setValue(list);
        }

        public LiveData<List<PageControlFragment>> getButtons() {
            return mButtons;
        }
    }*/
   /*public class SharedViewModel extends ViewModel {
       private final MutableLiveData<ClipData.Item> selected = new MutableLiveData<>();

       public void select(ClipData.Item item) {
           selected.setValue(item);
       }

       public LiveData<ClipData.Item> getSelected() {
           return selected;
       }
   }*/

   /* @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(URL_KEY, urlString);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }*/

    @Override
     public void selectWeb (String urlString){
            pageViewerFragment.showUrl(urlString);
     }

    @Override
    public void selectBack() {
      pageViewerFragment.showBack();
    }

    @Override
    public void selectForward() {
      pageViewerFragment.showForward();
    }

}