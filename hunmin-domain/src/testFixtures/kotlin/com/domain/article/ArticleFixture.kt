package com.domain.article

import com.domain.article.dto.ArticleSimpleResponse
import com.domain.article.dto.ArticlesSimpleResponse
import com.domain.article.vo.ArticleOptions
import java.time.Instant

class ArticleFixture {
    companion object {
        fun 일반_글_생성() =
            Article.createArticle(
                title = "글 제목", content = "글 내용",
                categoryId = 1L,
                writerId = 0L,
                isVisible = true,
                isQuestion = false,
            )

        fun 일반_글_생성_신고처리(): Article {
            val article = 일반_글_생성()
            return article.updateReport(reportState = true)
        }

        fun 일반_글_생성_작성자id(writerId: Long) =
            Article(
                title = "글 제목",
                content = "글 내용",
                categoryId = 1L,
                writerId = writerId,
                options = ArticleOptions(),
            )

        fun 삭제_글_생성_작성자id(writerId: Long) =
            Article(
                title = "글 제목",
                content = "글 내용",
                categoryId = 1L,
                writerId = writerId,
                options = ArticleOptions(isDeleted = true),
            )

        fun 일반_글_응답_생성() =
            ArticleSimpleResponse(
                articleId = 1L,
                title = "일반 글", content = "일반 글 내용",
                writerId = 1L, writerName = "작성자",
                categoryId = 1L, categoryName = "카테고리",
                isVisible = true, isReported = false,
                isTrending = false, isQuestion = false,
                isDeleted = false, createdAt = Instant.ofEpochMilli(1000)
            )

        fun 삭제_글_응답_생성() =
            ArticleSimpleResponse(
                articleId = 1L,
                title = "삭제 글", content = "삭제 글 내용",
                writerId = 1L, writerName = "작성자",
                categoryId = 1L, categoryName = "카테고리",
                isVisible = false, isReported = false,
                isTrending = false, isQuestion = false,
                isDeleted = true, createdAt = Instant.ofEpochMilli(1000)
            )

        fun 숨김_글_응답_생성_작성자id(writerId: Long) =
            ArticleSimpleResponse(
                articleId = 1L,
                title = "숨김 글", content = "숨김 글 내용",
                writerId = writerId, writerName = "작성자",
                categoryId = 1L, categoryName = "카테고리",
                isVisible = false, isReported = false,
                isTrending = false, isQuestion = false,
                isDeleted = false, createdAt = Instant.ofEpochMilli(1000)
            )

        fun 일반_글_페이징_생성() =
            ArticlesSimpleResponse(listOf(
                ArticleSimpleResponse(
                    articleId = 2L,
                    title = "제목 2",
                    content = "내용 2",
                    writerId = 1L,
                    writerName = "작성자",
                    categoryId = 1L,
                    categoryName = "카테고리",
                    isVisible = true,
                    isReported = false,
                    isTrending = false,
                    isQuestion = false,
                    isDeleted = false,
                    createdAt = Instant.ofEpochMilli(2000)
                ),
                ArticleSimpleResponse(
                    articleId = 1L,
                    title = "제목 1",
                    content = "내용 1",
                    writerId = 1L,
                    writerName = "작성자",
                    categoryId = 1L,
                    categoryName = "카테고리",
                    isVisible = true,
                    isReported = false,
                    isTrending = false,
                    isQuestion = false,
                    isDeleted = false,
                    createdAt = Instant.ofEpochMilli(1000)
                )
            ))
    }
}