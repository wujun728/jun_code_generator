
PS D:\workspace\scrm-paas> npm config set registry https://registry.npmmirror.com
PS D:\workspace\scrm-paas> npm install -g @colbymchenry/codegraph

added 2 packages in 23s
PS D:\workspace\scrm-paas> codegraph --version
0.9.4
PS D:\workspace\scrm-paas> codegraph build    
error: unknown command 'build'
PS D:\workspace\scrm-paas> codegraph init -i
┌  Initializing CodeGraph
│
◆  Initialized in D:\workspace\scrm-paas
|
|  * Scanning files - 23,482 found
|  * Parsing code - done
|  * Resolving refs - done
│
◆  Indexed 23,321 files
│
●  436,447 nodes, 444,556 edges in 9m 58s
│
└  Done

PS D:\workspace\scrm-paas> codegraph serve --mcp




# 1. 初始化 + 建索引（相当于旧版 build）
codegraph init -i

# 2. 查看索引状态（确认成功）
codegraph status

# 3. 启动 MCP 服务（给 Claude Code 用）
codegraph serve --mcp

codegraph init -i

codegraph sync



旧版（optave）	新版（colbymchenry）
codegraph build	codegraph init -i / codegraph index
codegraph watch	codegraph sync
codegraph mcp	codegraph serve --mcp
四、如果你是想装旧版（要 build 命令）
先卸载当前新版：
powershell
npm uninstall -g @colbymchenry/codegraph

codegraph update

五、Claude Code 配置不变
~/.claude.json 不用改，新版 / 旧版都兼容：
json
{
"mcpServers": {
"codegraph": {
"type": "stdio",
"command": "codegraph",
"args": ["serve", "--mcp"]
}
}
}