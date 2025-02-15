/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
  (8) 구독자 정보 모달 닫기
 */

function searchProfile() {
	let name = document.getElementById('search').value;

	$.ajax({
		type: "GET",
		url: "/api/search/" + name,
		dataType: "json"
	})
	.done(resp => {
		console.log(resp);
		resp.data.forEach((search) => {
			let item = getSearchItem(search);
			$("#searchList").append(item);
		});
	})
	.fail(error => {
		console.log("프로필 검색 실패", error);
	})
}

function getSearchItem(search) {
	let item = `

	`;

	return item;
}

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId, obj) {
	if ($(obj).text() === "구독취소") {

		$.ajax({
			type: "DELETE",
			url: "/api/subscribe/" + toUserId,
			dataType: "json"
		}).done(resp => {
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
		}).fail(error => {
			console.log("구독취소 실패", error);
		});
	} else {
		$.ajax({
			type: "POST",
			url: "/api/subscribe/" + toUserId,
			dataType: "json"
		}).done(resp => {
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");
		}).fail(error => {
			console.log("구독하기 실패", error);
		});
	}
}

// (2) 구독자 정보 모달 보기
function subscribeInfoModalOpen(pageUserId) {
	$(".modal-subscribe").css("display", "flex");

	$.ajax({
		url: `/api/user/${pageUserId}/subscribe`,
		dataType: "json"
	}).done(resp => {
		console.log(resp);
		resp.data.forEach((u) => {
			let item = getSubscribeModalItem(u);
			$("#subscribeModalList").append(item);
		});
	}).fail(error => {
		console.log(error);
	});
}

function getSubscribeModalItem(u) {
	let item = `<div class="subscribe__item" id="subscribeModalItem-${u.id}">
	<div class="subscribe__img">
		<img src="/upload/${u.profile_image_url}" onerror="this.src='/images/person.jpeg'" />
	</div>
	<div class="subscribe__text">
		<h2>${u.username}</h2>
	</div>
	<div class="subscribe__btn">`;

	if (!u.equal_user_state) {
		if (u.subscribe_state)
			item += `<button class="cta blue" onclick="toggleSubscribe(${u.id}, this)">구독취소</button>`
		else item += `<button class="cta" onclick="toggleSubscribe(${u.id}, this)">구독하기</button>`
	}

	item += `
	</div>
</div>`;

	return item;
}


/*// (3) 구독자 정보 모달에서 구독하기, 구독취소
function toggleSubscribeModal(obj) {
	if ($(obj).text() === "구독취소") {
		$(obj).text("구독하기");
		$(obj).toggleClass("blue");
	} else {
		$(obj).text("구독취소");
		$(obj).toggleClass("blue");
	}
}*/

// (4) 유저 프로파일 사진 변경 (완)
function profileImageUpload(principalId) {
	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}

		// FormData 객체를 이용하면 form 태그의 필드와 그 값을 나타내는 일련의 key/value 쌍을 담을 수 있음
		let userProfileImageForm = $("#userProfileImageForm")[0];
		let formData = new FormData(userProfileImageForm);

		// 서버에 이미지 전송하기
		$.ajax({
			type: "PUT",
			url: `/api/user/${principalId}/profileImageUrl`,
			data: formData,
			contentType: false,	// x-www-form-urlencoded로 파싱되는 것을 방지
			processData: false,	// contentType을 false로 설정할 경우 QueryString이 자동 설정되는 것을 방지
			enctype: "multipart/form-data",
			dataType: "json"
		}).done(resp => {
			// 사진 전송 성공시 이미지 변경
			let reader = new FileReader();
			reader.onload = (e) => {
				$("#userProfileImage").attr("src", e.target.result);
			}
			reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
		}).fail(error => {
			console.log(error);
		});
	});
}


// (5) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (8) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}
