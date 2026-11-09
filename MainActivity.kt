package com.manufacturingguru.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF073B78)
private val Blue = Color(0xFF1265D8)
private val Yellow = Color(0xFFFFC928)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ManufacturingGuruApp() }
    }
}

data class Feature(val title: String, val hindi: String, val icon: ImageVector, val color: Color, val description: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManufacturingGuruApp() {
    val features = listOf(
        Feature("CNC Training", "सीएनसी प्रशिक्षण", Icons.Default.PrecisionManufacturing, Blue,
            "Learn CNC, VMC, HMC and CNC Turning fundamentals, machine parts, offsets and safe operation."),
        Feature("Calculator", "कैलकुलेटर", Icons.Default.Calculate, Color(0xFF36B929),
            "RPM, feed rate and cutting speed calculators will be added in the next version."),
        Feature("G-Code & M-Code", "जी-कोड और एम-कोड", Icons.Default.Code, Color(0xFF7139D8),
            "Understand common G-codes and M-codes with simple Hindi + English explanations."),
        Feature("Machine SOP", "मशीन SOP", Icons.Default.Assignment, Color(0xFFFF9200),
            "Machine start-up, tool setting, job loading, inspection and shutdown SOP lessons."),
        Feature("Interview Questions", "इंटरव्यू प्रश्न", Icons.Default.QuestionAnswer, Color(0xFF08AFC4),
            "Practice operator, setter and production interview questions."),
        Feature("Daily Learning", "रोज सीखें", Icons.Default.MenuBook, Color(0xFF00A887),
            "Short manufacturing lessons and practical shop-floor tips.")
    )
    var selected by remember { mutableStateOf<Feature?>(null) }
    var tab by remember { mutableStateOf("Home") }

    MaterialTheme(colorScheme = lightColorScheme(primary = Navy, secondary = Yellow)) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text("Manufacturing Guru", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 21.sp)
                            Text("Learn • Practice • Grow | सीखें • अभ्यास करें • आगे बढ़ें", color = Color.White, fontSize = 10.sp)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Navy)
                )
            },
            bottomBar = {
                NavigationBar(containerColor = Navy) {
                    listOf("Home", "About", "Contact").forEach { item ->
                        NavigationBarItem(
                            selected = tab == item,
                            onClick = { tab = item; selected = null },
                            icon = {
                                Icon(
                                    when(item) { "Home" -> Icons.Default.Home; "About" -> Icons.Default.MenuBook; else -> Icons.Default.Call },
                                    contentDescription = item
                                )
                            },
                            label = { Text(item) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Yellow, selectedTextColor = Yellow,
                                unselectedIconColor = Color.White, unselectedTextColor = Color.White,
                                indicatorColor = Blue
                            )
                        )
                    }
                }
            }
        ) { padding ->
            if (selected != null) {
                FeatureDetail(selected!!, Modifier.padding(padding)) { selected = null }
            } else if (tab == "About") {
                InfoPage("About Manufacturing Guru", "A bilingual learning companion for CNC, VMC, HMC and manufacturing professionals.\n\nसीएनसी और मैन्युफैक्चरिंग सीखने के लिए हिंदी + English प्लेटफॉर्म।", Modifier.padding(padding))
            } else if (tab == "Contact") {
                InfoPage("Contact", "Contact details will be added before Play Store release.\n\nPlay Store पर प्रकाशित करने से पहले संपर्क विवरण जोड़े जाएंगे।", Modifier.padding(padding))
            } else {
                Column(
                    modifier = Modifier.padding(padding).fillMaxSize()
                        .background(Color(0xFFF3F7FC)).padding(14.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Navy),
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(18.dp)) {
                            Text("Your Complete Guide for", color = Color.White, fontSize = 17.sp)
                            Text("CNC & Manufacturing", color = Yellow, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold)
                            Text("सीखें • कौशल बढ़ाएँ • करियर बनाएँ", color = Color.White, fontSize = 15.sp)
                            Spacer(Modifier.height(8.dp))
                            Text("Skill bhi… Career bhi…", color = Yellow, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Explore Features | मुख्य सुविधाएँ", color = Navy, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(10.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(features) { feature ->
                            Card(
                                modifier = Modifier.fillMaxWidth().height(148.dp).clickable { selected = feature },
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = feature.color),
                                elevation = CardDefaults.cardElevation(3.dp)
                            ) {
                                Column(
                                    Modifier.fillMaxSize().padding(10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(feature.icon, null, tint = Color.White, modifier = Modifier.size(38.dp))
                                    Spacer(Modifier.height(8.dp))
                                    Text(feature.title, color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 15.sp)
                                    Text(feature.hindi, color = Color.White, textAlign = TextAlign.Center, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                    Text("Practical Knowledge • Easy Learning • Career Guidance", color = Navy, fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
                }
            }
        }
    }
}

@Composable
fun FeatureDetail(feature: Feature, modifier: Modifier = Modifier, onBack: () -> Unit) {
    Column(modifier.fillMaxSize().background(Color(0xFFF3F7FC)).padding(20.dp)) {
        Text("← Back", color = Blue, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onBack() }.padding(bottom = 20.dp))
        Icon(feature.icon, null, tint = feature.color, modifier = Modifier.size(54.dp))
        Spacer(Modifier.height(12.dp))
        Text(feature.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Navy)
        Text(feature.hindi, fontSize = 17.sp, color = Color.DarkGray)
        Spacer(Modifier.height(18.dp))
        Text(feature.description, fontSize = 16.sp, lineHeight = 24.sp)
        Spacer(Modifier.height(12.dp))
        Text("More lessons and tools will be added in upcoming versions.\nअगले संस्करणों में और पाठ व टूल जोड़े जाएंगे।", color = Color.Gray)
    }
}

@Composable
fun InfoPage(title: String, body: String, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().background(Color(0xFFF3F7FC)).padding(22.dp)) {
        Text(title, fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Navy)
        Spacer(Modifier.height(16.dp))
        Text(body, fontSize = 16.sp, lineHeight = 25.sp)
    }
}
