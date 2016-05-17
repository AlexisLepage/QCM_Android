package com.iia.cdsm.myqcm.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.data.QcmSQLiteAdapter;

/**
 * Created by Alexis on 17/05/2016.
 */
public class ViewQcmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_qcm);

        final ListView lvQcms = (ListView)this.findViewById(R.id.lv_qcms);

        QcmSQLiteAdapter qcmSQLiteAdapter = new QcmSQLiteAdapter(this);

        qcmSQLiteAdapter.open();
        qcmSQLiteAdapter.getAllQcm();
        qcmSQLiteAdapter.close();
    }
}
