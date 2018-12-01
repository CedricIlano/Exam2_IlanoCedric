package cedrickristoffer.ilano.com.practicalexamno2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference root;
    EditText eFname, eLname, eExam1, eExam2;
    ArrayList<String> keyList;
    TextView textViewRes;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance();
        root = db.getReference("root");
        eFname = findViewById(R.id.etFN);
        eLname = findViewById(R.id.etLN);
        eExam1 = findViewById(R.id.etExam1);
        eExam2 = findViewById(R.id.etExam2);
        keyList = new ArrayList<>();
        textViewRes = findViewById(R.id.textViewRes);

    }

    @Override
    protected void onStart() {
        super.onStart();
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ss : dataSnapshot.getChildren()) {
                    keyList.add(ss.getKey());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void moveFirst(View v) {

        String fName = eFname.getText().toString().trim();
        String lName = eLname.getText().toString().trim();
        Long firstNum = Long.parseLong(eExam1.getText().toString().trim());
        Long secondNum = Long.parseLong(eExam2.getText().toString().trim());
        Long average = (firstNum + secondNum) / 2;

        Student sgrade = new Student(fName,lName,average);
        String key = root.push().getKey();
        root.child(key).setValue(sgrade);
        keyList.add(key);

        textViewRes.setText("Your average is: " + average + "");



    }


}

