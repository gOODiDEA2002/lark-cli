package main

import (
	"github.com/cuigh/auxo/app"
	"github.com/cuigh/auxo/app/flag"
	"github.com/cuigh/auxo/config"
	"github.com/cuigh/lark/cmd"
	"github.com/cuigh/lark/tpl"
	"github.com/gobuffalo/packr"
)

func main() {
	config.SetDefaultValue("banner", false)
	tpl.SetBox(packr.NewBox("./assets"))

	app.Name = "lark-cli"
	app.Version = "1.5.0"
	app.Desc = "A tool for developing lark-1.5 based application"
	app.Action = cmd.Root
	app.AddCommand(cmd.New())
	app.AddCommand(cmd.Gen())
	app.Flags.Register(flag.Help | flag.Version)
	app.Start()
}
