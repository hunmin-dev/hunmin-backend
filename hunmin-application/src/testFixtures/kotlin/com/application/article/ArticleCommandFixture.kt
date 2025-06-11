package com.application.article

import com.domain.article.port.`in`.command.CreateCommand

class ArticleCommandFixture {
    companion object {

        fun 일반_글_생성_커맨드() =
            CreateCommand(
                title = "글 제목", content = "글 내용", categoryId = 1L
            )
    }
}
