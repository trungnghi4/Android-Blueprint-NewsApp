package github.ntn.blueprint.ui.savednews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.ntn.blueprint.data.model.Article
import github.ntn.blueprint.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val savedArticleEventChannel = Channel<SavedArticleEvent>()
    val savedArticleEvent = savedArticleEventChannel.receiveAsFlow()

    fun getAllArticles() = newsRepository.getAllArticles()

    fun onArticleSwiped(article: Article) {
        viewModelScope.launch {
            newsRepository.deleteArticle(article)
            savedArticleEventChannel.send(SavedArticleEvent.ShowUndoDeleteArticleMessage(article))
        }
    }

    fun onUndoDeleteClick(article: Article) {
        viewModelScope.launch {
            newsRepository.insertArticle(article)
        }
    }

    sealed class SavedArticleEvent{
        data class ShowUndoDeleteArticleMessage(val article: Article): SavedArticleEvent()
    }
}