import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Token(
    val id: Int,
    val name: String,
    val symbol: String,
    val price: String, // $1.18
    val subText: String, // MCap: $2.3B • Vol: $62B (Using subText for MCap/Vol)
    val changePercent: Double, // +2.77
    val icon: ImageVector, // Icon
    val iconColor: Color,
    val mCapVol: String? = null // Optional extra field
)