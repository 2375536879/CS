function handleLogin() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // 这里只是一个简单的验证示例，实际应用中需要更复杂的验证逻辑和与后端的交互
    if (username === 'admin' && password === '123456') {
        alert('登录成功！');
        // 在这里可以重定向到系统主页面
    } else {
        alert('用户名或密码错误');
    }
}