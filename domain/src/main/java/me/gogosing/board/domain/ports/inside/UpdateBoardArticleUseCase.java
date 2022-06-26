package me.gogosing.board.domain.ports.inside;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import me.gogosing.board.application.port.in.request.command.UpdateBoardArticleInCommand;
import me.gogosing.board.application.port.in.response.UpdateBoardArticleInResponse;

public interface UpdateBoardArticleUseCase {

	// 유즈케이스에 Response,Request가 없도록 개선되면 어떨까 싶습니다.
	// Response, Request가 유즈케이스에 있으면, 이는 도메인이 해당 인프라(여기서는 API)에 종속되었음을 나타내는 거 같아요.
	// 도메인이 각 인프라의 기술적인 측면으로부터 자유롭게 구성되는 게 헥사고날(포트&어댑터 패턴)의 주요 목적 중 하나라 제안드린 방향으로 변경되면 어떨까 싶습니다.
	UpdateBoardArticleInResponse updateBoardArticle(
		final @Min(1L) Long id,
		final @Valid UpdateBoardArticleInCommand inCommand
	);

}
