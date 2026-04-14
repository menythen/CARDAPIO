package br.fatec.garca.cardapio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CafeMenuScreen()
            }
        }
    }
}

data class CafeItem(
    val nome: String,
    val descricao: String,
    val preco: Double,
    var quantidade: Int = 0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CafeMenuScreen() {

    val fundo = Color(0xFFF3E9DC)
    val marromEscuro = Color(0xFF4E342E)
    val marromMedio = Color(0xFF8D6E63)
    val bege = Color(0xFFD7CCC8)
    val creme = Color(0xFFEFEBE9)

    val cafes = remember {
        mutableStateListOf(
            CafeItem(
                "Capuccino",
                "Café espresso, leite vaporizado, espuma e canela ou chocolate.",
                12.50
            ),
            CafeItem(
                "Mocca",
                "Café espresso, leite vaporizado, chocolate e espuma.",
                14.00
            ),
            CafeItem(
                "Latte",
                "Café espresso com bastante leite vaporizado e leve espuma.",
                11.00
            ),
            CafeItem(
                "Espresso",
                "Café forte e concentrado feito sob pressão.",
                8.00
            ),
            CafeItem(
                "Macchiato",
                "Espresso com toque de leite vaporizado.",
                10.50
            )
        )
    }

    val totalItens = cafes.sumOf { it.quantidade }
    val valorTotal = cafes.sumOf { it.quantidade * it.preco }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Cardápio da Cafeteria",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = marromEscuro
                )
            )
        },
        containerColor = fundo
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            // RESUMO
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = creme),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        "Resumo",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = marromEscuro
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Itens: $totalItens", color = marromMedio)

                    Text(
                        "Total: R$ ${String.format("%.2f", valorTotal)}",
                        fontWeight = FontWeight.Bold,
                        color = marromEscuro
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            cafes.forEachIndexed { index, item ->
                                cafes[index] = item.copy(quantidade = 0)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = marromMedio)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Limpar")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LISTA
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                itemsIndexed(cafes) { index, item ->

                    Card(
                        colors = CardDefaults.cardColors(containerColor = bege),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                item.nome,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = marromEscuro
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(item.descricao)

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                "R$ ${String.format("%.2f", item.preco)}",
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text("Qtd:")

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Button(
                                        onClick = {
                                            if (item.quantidade > 0) {
                                                    cafes[index] = item.copy(quantidade = item.quantidade - 1)
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = marromEscuro
                                        )
                                    ) {
                                        Text("-")
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        item.quantidade.toString(),
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Button(
                                        onClick = {
                                            cafes[index] = item.copy(quantidade = item.quantidade - 1)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = marromEscuro
                                        )
                                    ) {
                                        Text("+")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}