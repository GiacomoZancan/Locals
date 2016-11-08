package com.locals;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Giacomo on 17/10/2016.
 */

public class SchermataLocali extends Fragment {
    private ListView lv;
    MyCustomAdapter dataAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.locali_layout, container, false);
        lv = (ListView) view.findViewById(R.id.listView);
        getActivity().setTitle("SchermataLocali");
        new GetAllCustomerTask().execute(new ApiConnector());
        return view;
    }


    public void setTextToTextView(JSONArray jsonArray) {
        int n = 0;
        String s = "";
        ArrayList<Locals> lista = new ArrayList<Locals>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject json = null;
                try {
                    n = jsonArray.length() - 1 - i;
                    json = jsonArray.getJSONObject(n);
                    lista.add(new Locals(json.getString("nome")));
                    dataAdapter = new MyCustomAdapter(getActivity(), R.layout.cell_list, lista);
                    // Assign adapter to ListView
                    lv.setAdapter(dataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }



    private class GetAllCustomerTask extends AsyncTask<ApiConnector, Long, JSONArray> {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {


            // it is executed on Background thread

            return params[0].GetAllCustomers();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setTextToTextView(jsonArray);
            //refresh.setRefreshing(false);


        }
    }

    private class MyCustomAdapter extends ArrayAdapter<Locals> {

        private ArrayList<Locals> lista;

        public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<Locals> lista) {
            super(context, textViewResourceId, lista);
            this.lista = new ArrayList<Locals>();
            this.lista.addAll(lista);
        }

        private class ViewHolder {
            TextView nome;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.cell_list, null);

                holder = new ViewHolder();
                holder.nome = (TextView) convertView.findViewById(R.id.textView3);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Locals lo = lista.get(position);
            holder.nome.setText(lo.getNome());
            holder.nome.setTag(lo);
            holder.nome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment vFragment = new LocaleDisplay();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container, vFragment);
                    ft.commit();
                }
            });

            return convertView;
        }
    }

/*    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                // Getting intitial by event action down
                initialX = event.getX();
                break;

            case MotionEvent.ACTION_UP:

                // On action up the flipper will start and showing next item
                float finalX = event.getX();
                if (initialX > finalX) {

                    // Show items are 4
                    if (flip.getDisplayedChild() == 4)
                        break;

                    // Flip show next will show next item
                    flip.showNext();
                } else {

                    // If flip has no items more then it will display previous item
                    if (flip.getDisplayedChild() == 0)
                        break;
                    flip.showPrevious();
                }
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.flip_by_interval:

                // This will set the flipper automatic and timing is 2sec between
                // flipping
                flip.setFlipInterval(2000);
                flip.startFlipping();
                break;

            case R.id.flip_on_click:

                // If flipper is already flipping it will stop flipping and show
                // next if not flipping
                if (flip.isFlipping()) {
                    flip.stopFlipping();
                }
                flip.showNext();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
