package android.ait.hu.shoppinglist;

import android.ait.hu.shoppinglist.data.Item;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.math.BigDecimal;
import java.util.Date;

public class CreateItemActivity extends AppCompatActivity {

    public static final String KEY_ITEM = "KEY_ITEM";
    private Spinner spinnerItemType;
    private EditText etItemName;
    private EditText etItemDesc;
    private EditText etItemPrice;
    private Item itemtoEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        if (getIntent().getSerializableExtra(MainActivity.KEY_EDIT) != null) {
            itemtoEdit = (Item) getIntent().getSerializableExtra(MainActivity.KEY_EDIT);
        }
        spinnerItemType = (Spinner) findViewById(R.id.spinnerItemType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.itemtypes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItemType.setAdapter(adapter);

        etItemName = (EditText) findViewById(R.id.etItemName);
        etItemDesc = (EditText) findViewById(R.id.etItemDesc);
        etItemPrice = (EditText) findViewById(R.id.etItemPrice);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });

        if (itemtoEdit != null) {
            etItemName.setText(itemtoEdit.getItem());
            etItemDesc.setText(itemtoEdit.getDescription());
            etItemPrice.setText(String.format("%.2f", itemtoEdit.getPrice()));
            spinnerItemType.setSelection(itemtoEdit.getItemType().getValue());
        }
    }

    private void saveItem(){
        Intent intentResult = new Intent();
        Item itemResult;
        if (itemtoEdit != null){
            itemResult = itemtoEdit;
        }else{
            itemResult = new Item();
        }

        if (!"".equals(etItemName.getText().toString())){
            itemResult.setItem(etItemName.getText().toString());
        } else{
            itemResult.setItem("");
        }
        if (!"".equals(etItemDesc.getText().toString())){
            itemResult.setDescription(etItemDesc.getText().toString());
        }else{
            itemResult.setItem("");
        }
        itemResult.setPrice(itemtoEdit.getPrice());


        itemResult.setItemType(
                Item.ItemType.fromInt(spinnerItemType.getSelectedItemPosition()));

        intentResult.putExtra(KEY_ITEM, itemResult);
        setResult(RESULT_OK, intentResult);
        finish();
    }

}
