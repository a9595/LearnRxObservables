package edu.tieorange.learnrxobservables;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getCanonicalName();

  public static class ObservableList<T> {

    protected final List<T> list;
    protected final PublishSubject<T> onAdd;

    public ObservableList() {
      this.list = new ArrayList<T>();
      this.onAdd = PublishSubject.create();
    }

    public void add(T value) {
      list.add(value);
      onAdd.onNext(value);
    }

    public Observable<T> getObservable() {
      return onAdd;
    }
  }

  private List<String> stringsList = new ArrayList<>();
  Observable<String> mObservableString = Observable.from(stringsList);

  Observer<String> mStringObserver = new Observer<String>() {
    @Override public void onCompleted() {
      Log.d(TAG, "onCompleted() called");
    }

    @Override public void onError(Throwable e) {

    }

    @Override public void onNext(String s) {
      Log.d(TAG, "onNext() called with: s = [" + s + "]");
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initRx();

    mObservableString.subscribe(mStringObserver);

    initNetworkCall();

    /*String[] names = new String[1];
    Observable.from(names).subscribe(new Action1<String>() {

      @Override public void call(String s) {
        System.out.println("Hello " + s + "!");
      }
    });

    names[0] = "h";*/
    //myObservable

    initObservableLIst();
  }

  private void initNetworkCall() {
    for (int i = 0; i < 3; i++) {
      stringsList.add("App #" + i);
    }
  }

  private void initObservableLIst() {
    ObservableList<Integer> olist = new ObservableList<>();

    olist.getObservable().subscribe(new Observer<Integer>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onNext(Integer integer) {
        Log.d(TAG, "onNext() called with: integer = [" + integer + "]");
      }
    });

    olist.add(1);
    olist.add(2);
    olist.add(3);
  }

  private void initRx() {
    Observable<String> myObservable = Observable.just("Hello"); // Emits "Hello"

    Observer<String> myObserver = new Observer<String>() {
      @Override public void onCompleted() {
        // Called when the observable has no more data to emit
      }

      @Override public void onError(Throwable e) {
        // Called when the observable encounters an error
      }

      @Override public void onNext(String s) {
        // Called each time the observable emits data
        Log.d("MY OBSERVER", s);
      }
    };

    Subscription mySubscription = myObservable.subscribe(myObserver);
  }
}
