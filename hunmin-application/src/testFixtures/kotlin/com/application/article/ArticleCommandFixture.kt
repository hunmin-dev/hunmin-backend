package com.application.article

import com.domain.article.Article
import com.domain.article.port.`in`.command.CreateCommand
import com.domain.article.vo.ArticleOptions

class ArticleCommandFixture {
    companion object {
        fun 일반_글_생성(): Article =
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

        fun 일반_글_생성_작성자id(writerId: Long): Article =
            Article(
                title = "글 제목",
                content = "글 내용",
                categoryId = 1L,
                writerId = writerId,
                options = ArticleOptions(),
            )

        fun 삭제_글_생성_작성자id(writerId: Long): Article =
            Article(
                title = "글 제목",
                content = "글 내용",
                categoryId = 1L,
                writerId = writerId,
                options = ArticleOptions(isDeleted = true),
            )

        fun 숨김_글_생성_작성자id(writerId: Long): Article =
            Article(
                title = "글 제목",
                content = "글 내용",
                categoryId = 1L,
                writerId = writerId,
                options = ArticleOptions(isVisible = false)
            )
        fun 일반_글_생성_커맨드(): CreateCommand =
            CreateCommand(
                title = "글 제목", content = "글 내용", categoryId = 1L
            )
    }
}
