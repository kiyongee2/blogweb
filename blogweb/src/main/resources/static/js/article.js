// 응답이 실패(4xx, 5xx)면 오류를 던진다 → catch에서 알림
function checkResponse(response) {
    if (!response.ok) {
        throw new Error(`요청 실패 (${response.status})`);
    }
    return response;
}

// 제목·내용이 비어 있으면 서버로 보내지 않는다 (DB에서 NOT NULL)
function readForm() {
    const title = document.getElementById('title').value.trim();
    const content = document.getElementById('content').value.trim();
    if (!title || !content) {
        alert('제목과 내용을 입력하세요.');
        return null;
    }
    return {
        title: title,
        content: content,
        imageUrl: document.getElementById('image-url')?.value || null
    };
}

// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        fetch(`/api/articles/${id}`, {
            method: 'DELETE'
        })
        .then(checkResponse)
        .then(() => {
            alert('삭제가 완료되었습니다.');
            location.replace('/articles');
        })
        .catch(error => alert('삭제 실패: ' + error.message));
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');
        const body = readForm();
        if (!body) return;

        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)   // imageUrl까지 보내야 이미지가 지워지지 않는다
        })
        .then(checkResponse)
        .then(() => {
            alert('수정이 완료되었습니다.');
            location.replace(`/articles/${id}`);
        })
        .catch(error => alert('수정 실패: ' + error.message));
    });
}

// 등록 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        const body = readForm();
        if (!body) return;

        fetch('/api/articles', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        })
        .then(checkResponse)
        .then(() => {
            alert('등록 완료되었습니다.');
            location.replace('/articles');
        })
        .catch(error => alert('등록 실패: ' + error.message));
    });
}
