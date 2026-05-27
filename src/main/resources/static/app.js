const output = document.querySelector("#terminalOutput");
const form = document.querySelector("#commandForm");
const input = document.querySelector("#commandInput");
const promptLabel = document.querySelector("#promptLabel");
const resetButton = document.querySelector("#resetButton");

let sessionId = window.localStorage.getItem("exquest.sessionId");

function appendLine(text, className = "") {
    const line = document.createElement("div");
    line.className = `line ${className}`.trim();
    line.textContent = text;
    output.appendChild(line);
}

function appendLines(lines) {
    for (const text of lines) {
        const parts = String(text).split("\n");
        for (const part of parts) {
            appendLine(part, part.includes("WARNING") ? "system" : "");
        }
    }
    output.scrollTop = output.scrollHeight;
}

function setPrompt(prompt) {
    promptLabel.textContent = prompt || "Your Response";
}

async function createSession() {
    const response = await fetch("/api/session", { method: "POST" });
    const payload = await response.json();
    sessionId = payload.sessionId;
    window.localStorage.setItem("exquest.sessionId", sessionId);
    appendLines(payload.lines);
    setPrompt(payload.prompt);
}

async function sendCommand(command) {
    appendLine(`${promptLabel.textContent} - ${command}`, "command");
    const response = await fetch(`/api/session/${sessionId}/command`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ command })
    });
    const payload = await response.json();
    appendLines(payload.lines);
    setPrompt(payload.prompt);
}

async function resetSession() {
    output.replaceChildren();
    if (!sessionId) {
        await createSession();
        return;
    }
    const response = await fetch(`/api/session/${sessionId}/reset`, { method: "POST" });
    const payload = await response.json();
    appendLines(payload.lines);
    setPrompt(payload.prompt);
    input.focus();
}

form.addEventListener("submit", async (event) => {
    event.preventDefault();
    const command = input.value.trim();
    input.value = "";
    if (!command) {
        input.focus();
        return;
    }
    try {
        await sendCommand(command);
    } catch (error) {
        appendLine("Terminal connection failed. Check the Spring Boot server.", "system");
    } finally {
        input.focus();
    }
});

resetButton.addEventListener("click", resetSession);

createSession().catch(() => {
    appendLine("Terminal connection failed. Check the Spring Boot server.", "system");
});
