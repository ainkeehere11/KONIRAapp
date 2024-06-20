import android.content.Context
import com.dicoding.koniraapp.R
import com.dicoding.koniraapp.ui.news.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

object JsonUtils {

    fun loadArticles(context: Context): List<Article> {
        val inputStream = context.resources.openRawResource(R.raw.article)
        val reader = InputStreamReader(inputStream)
        val articleType = object : TypeToken<List<Article>>() {}.type
        return Gson().fromJson(reader, articleType)
    }
}