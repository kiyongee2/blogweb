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

// 파일 선택시 업로드 + 미리보기
const imageUpload = document.getElementById('image-upload');
if (imageUpload) {
    imageUpload.addEventListener('change', (event) => {
        const file = event.target.files[0];
        if (!file) return;
        if (!file.type.startsWith('image/')) {
            alert('이미지 파일만 업로드 가능합니다.');
            return;
        }
        const formData = new FormData();
        formData.append('file', file);           // @RequestParam("file") 과 이름 일치

        fetch('/api/upload', { method: 'POST', body: formData })
            .then(response => {
                if (!response.ok) throw new Error('업로드 실패');
                return response.json();
            })
            .then(data => {
                document.getElementById('image-url').value = data.imageUrl;
                displayImagePreview(data.imageUrl);
            })
            .catch(e => alert(e.message));
    });
}
// 미리보기 표시 — 업로드한 이미지 경로를 <img>에 넣고 미리보기 영역을 보여 준다
function displayImagePreview(imageUrl) {
    const preview = document.getElementById('image-preview');
    const previewImg = document.getElementById('preview-img');
    if (!preview || !previewImg) return;
    previewImg.src = imageUrl;
    preview.style.display = 'block';
}

// 이미지 제거 — 경로를 비우고 미리보기를 숨긴다 (저장/수정 시 imageUrl = null)
const removeImageButton = document.getElementById('remove-image-btn');
if (removeImageButton) {
    removeImageButton.addEventListener('click', () => {
        document.getElementById('image-url').value = '';
        document.getElementById('image-upload').value = '';
        document.getElementById('preview-img').src = '';
        document.getElementById('image-preview').style.display = 'none';
    });
}

// 수정 화면 — 이미 저장된 이미지가 있으면 처음부터 미리보기 표시
const savedImageUrl = document.getElementById('image-url')?.value;
if (savedImageUrl) {
    displayImagePreview(savedImageUrl);
}
