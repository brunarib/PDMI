package com.example.vamos_rachar2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    EditText et_a_pagar;
    TextView tv_porcentagem_valor, tv_pessqa_qtd, tv_valor_total_porcentagem, tv_valor_total;
    Button b_calcular, b_pessoa_add, b_pessoa_sub, b_porcentagem_add, b_porcentagem_sub,b_compartilhar;

    int countPessoa = 0;
    int taxaPorcentagem = 0;

    float conta = 0;
    float porcentagem = 0;
    float contaPessoa = 0;
    float contaTotal =0;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_a_pagar = findViewById(R.id.et_a_pagar);
        tv_pessqa_qtd = findViewById(R.id.tv_pessqa_qtd);
        tv_porcentagem_valor = findViewById(R.id.tv_porcentagem_valor);
        tv_valor_total = findViewById(R.id.tv_valor_total);
        tv_valor_total_porcentagem = findViewById(R.id.tv_valor_total_porcentagem);
        b_pessoa_add = findViewById(R.id.b_pessoa_add);
        b_pessoa_sub = findViewById(R.id.b_pessoa_sub);
        b_porcentagem_add = findViewById(R.id.b_porcentagem_add);
        b_porcentagem_sub = findViewById(R.id.b_porcentagem_sub);
        b_compartilhar= findViewById(R.id.b_compartilhar);

        b_pessoa_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countPessoa > 0) {
                    countPessoa--;
                    tv_pessqa_qtd.setText(countPessoa + "");
                    calcular();
                }
            }
        });

        b_pessoa_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countPessoa++;
                tv_pessqa_qtd.setText(countPessoa + "");
                calcular();

            }
        });

        b_porcentagem_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taxaPorcentagem > 0) {
                    taxaPorcentagem--;
                    tv_porcentagem_valor.setText(taxaPorcentagem + "%");
                    calcular();
                }
            }
        });

        b_porcentagem_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taxaPorcentagem++;
                tv_porcentagem_valor.setText(taxaPorcentagem + "%");
                calcular();

            }
        });


        b_compartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               compartilharConta();

            }
        });


    }

    private Float formatNumber(float valor) {
        float result = 0.00F;

        result = valor * 100;
        result = Math.round(result);
        result = result / 100;

        return result;

    }


    private void calcular() {

        String contaAPagar = et_a_pagar.getText().toString();

        if (!contaAPagar.equals("")) {
            conta = Float.valueOf(contaAPagar);
            conta = formatNumber(conta);
            et_a_pagar.setText(conta + "");

            porcentagem = (conta * taxaPorcentagem) / 100;
            porcentagem = formatNumber(porcentagem);

            tv_valor_total_porcentagem.setText(porcentagem + "");

            contaTotal = conta + porcentagem;
            contaTotal= formatNumber(contaTotal);

            contaPessoa = contaTotal / countPessoa;

            tv_valor_total.setText(contaPessoa + "");
        }
    }

    private void compartilharConta(){
        StringBuilder ms = new StringBuilder();
        ms.append(getResources().getString(R.string.a_pagar)+":"+ conta);
        ms.append("\n"+getResources().getString(R.string.taxa_servico)+":"+ taxaPorcentagem +"%");
        ms.append("\n"+getResources().getString(R.string.pessoas_qtd)+":"+ countPessoa);
        ms.append("\n"+getResources().getString(R.string.valor_conta)+":" + contaTotal);
        ms.append("\n"+getResources().getString(R.string.valor_servico)+":" + porcentagem);
        ms.append("\n"+getResources().getString(R.string.valor_pessoa)+":"+ contaPessoa);


        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,String.valueOf(ms));
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, getResources().getString(R.string.valor_pessoa));
        startActivity(shareIntent);
    }
}