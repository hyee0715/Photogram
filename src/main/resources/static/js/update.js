// (1) 회원정보 수정
function update(user_id, event) {
	event.preventDefault();	//form 태그 액션 막기
	let data = $("#profileUpdate").serialize();

	$.ajax({
		type:"put",
		url:`/api/user/${user_id}`,
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType: "json"
	}).done(resp => {	//Http status: 2xx
		location.href=`/user/${user_id}`;
	}).fail(error => {
		if(error.data == null)
			alert(error.responseJSON.message);
		else
			alert(JSON.stringify(error.responseJSON.data));
	})
}