package com.example.proiectdam.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proiectdam.R;
import com.example.proiectdam.data.GetGradesAsync;
import com.example.proiectdam.data.GetUsersAsync;
import com.example.proiectdam.data.Grade;
import com.example.proiectdam.data.User;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.util.List;

public class TeacherProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.teacherFragmentContainer, new ViewStudentsFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.teacher_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.addStudent:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.teacherFragmentContainer,new AddUserFragment()).commit();
                break;
            case R.id.viewStudents:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.teacherFragmentContainer,new ViewStudentsFragment()).commit();
                break;
            case R.id.saveStudents:
                saveStudentsToCSV();
                Toast.makeText(this, "Saving...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewGraphic:
                loadGraphic();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void saveStudentsToCSV() {
        try{

            String csv = getBaseContext().getFilesDir().toString() + "/studenti.csv";

            final CSVWriter writer = new CSVWriter(new FileWriter(csv));

            String [] header = "Nume#Prenume#Email".split("#");
            writer.writeNext(header);

            new GetUsersAsync(this){

                @Override
                protected void onPostExecute(List<User> users) {
                    super.onPostExecute(users);
                   for(User u : users)
                   {
                       String[] student = new String[3];
                       student[0] = u.getNume();
                       student[1] = u.getPrenume();
                       student[2] = u.getEmail();
                       writer.writeNext(student);
                   }
                    try{
                        writer.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }.execute("stud");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    private void loadGraphic() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(TeacherProfile.this);
        LayoutInflater inflater = TeacherProfile.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.graphic_dialog, null);
        final LinearLayout linearLayout = view.findViewById(R.id.graphicLinearLayout);

        new GetGradesAsync(this){
            @Override
            protected void onPostExecute(List<Grade> grades) {
                super.onPostExecute(grades);
                float[] gradesArray = new float[10];
                for(Grade g : grades) {
                    gradesArray[g.getNota() - 1]++;
                }
                    PieChartView pieChartView = new PieChartView(TeacherProfile.this,calculatePieChartValues(gradesArray));

                    linearLayout.addView(pieChartView);

                    builder.setView(view);
                    builder.setTitle("Grafic note");

                    builder.setNegativeButton("Inchide", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface di, int i) {
                        }
                    });
                    builder.create().show();

            }
        }.execute();

    }

    private float[] calculatePieChartValues(float[] values)
    {
        float[] degreeValues = new float[values.length];
        float total = 0;
        for(int i=0;i<values.length;i++)
        {
            total += values[i];
        }
        for(int i=0;i<values.length;i++)
        {
            degreeValues[i] = 360 * (values[i]/total);
        }
        return degreeValues;
    }


    class PieChartView extends View {

        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private float[] valuesDegrees;
        private int[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.RED, Color.GREEN,
                                Color.MAGENTA,Color.YELLOW,Color.GRAY,Color.DKGRAY,Color.LTGRAY};
        private RectF rectangle = new RectF(10,10,500,500);
        private int tmp;

        public PieChartView(Context context, float[] values) {
            super(context);
            valuesDegrees = new float[values.length];
            for(int i=0;i<values.length;i++)
            {
                valuesDegrees[i] = values[i];
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int y = 600;
            for(int i=0;i<valuesDegrees.length;i++)
            {
                if(i==0)
                {
                    paint.setColor(colors[i]);
                    canvas.drawArc(rectangle,0,valuesDegrees[i],true,paint);
                    paint.setTextSize(75);
                    canvas.drawText(String.valueOf(i+1),25,y,paint);
                    y += 85;
                }
                else
                {
                    tmp += valuesDegrees[i-1];
                    paint.setColor(colors[i]);
                    canvas.drawArc(rectangle,tmp, valuesDegrees[i],true,paint);
                    paint.setTextSize(75);
                    canvas.drawText(String.valueOf(i+1),25,y,paint);
                    y += 85;
                }
            }
        }
    }
}

