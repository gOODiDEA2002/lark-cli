package cmd

import (
	"fmt"
	"os"
	"path/filepath"
	"strings"

	"github.com/cuigh/auxo/app"
	"github.com/cuigh/auxo/app/flag"
	"github.com/cuigh/auxo/config"
	"github.com/cuigh/auxo/errors"
	"github.com/cuigh/lark/tpl"
	"github.com/cuigh/lark/util/file"
	"github.com/cuigh/lark/util/pom"
)

func New() *app.Command {
	cmd := app.NewCommand("new", "Create a project or module.", func(ctx *app.Context) error {
		fmt.Println("Usage: lark-cli new project|module")
		return nil
	})
	cmd.Flags.Register(flag.Help)
	cmd.AddCommand(NewProject())
	cmd.AddCommand(NewModule("service"))
	cmd.AddCommand(NewModule("service-contract"))
	cmd.AddCommand(NewModule("api"))
	cmd.AddCommand(NewModule("api-contract"))
	cmd.AddCommand(NewModule("msg-contract"))
	cmd.AddCommand(NewModule("msg-handler"))
	cmd.AddCommand(NewModule("task"))
	return cmd
}

// NewProject create `project` sub command
func NewProject() *app.Command {
	desc := "Create a project."
	cmd := app.NewCommand("project", desc, func(ctx *app.Context) error {
		args := &struct {
			Group    string `option:"group"`
			Artifact string `option:"artifact"`
		}{}
		if err := config.Unmarshal(args); err != nil {
			return err
		}

		if args.Group == "" {
			return errors.New("group is missing")
		}

		wd, err := os.Getwd()
		if err != nil {
			return errors.Wrap(err, "acquire work directory failed")
		}

		if len(ctx.Args()) == 0 {
			return errors.New("project name is missing")
		}

		name := ctx.Args()[0]
		if args.Artifact == "" {
			args.Artifact = name
		}

		// check dir exist
		dir := filepath.Join(wd, name)
		if file.Exist(dir) {
			return errors.New("directory already exist: " + dir)
		}

		data := map[string]string{
			"GroupID":    args.Group,
			"ArtifactID": args.Artifact,
		}

		// create files
		files := make(map[string]string)
		files[filepath.Join(dir, "pom.xml")] = "project/pom.xml"
		files[filepath.Join(dir, "README.md")] = "project/README.md"
		files[filepath.Join(dir, ".gitignore")] = "project/gitignore"
		files[filepath.Join(dir, ".gitlab-ci.yml")] = "project/gitlab-ci.yml"
		if err = tpl.Execute(files, data); err != nil {
			return err
		}

		fmt.Println("finished.")
		return nil
	})
	cmd.Flags.Register(flag.Help)
	cmd.Flags.String("group", "g", "", "group id")
	cmd.Flags.String("artifact", "a", "", "artifact id")
	return cmd
}

// NewModule create `new service/msg/task/web` sub command
func NewModule(moduleType string) *app.Command {
	desc := fmt.Sprintf("Create a %s module.", moduleType)
	cmd := app.NewCommand(moduleType, desc, func(ctx *app.Context) error {
		args := &struct {
			Group    string `option:"group"`
			Artifact string `option:"artifact"`
			Package  string `option:"package"`
		}{}
		if err := config.Unmarshal(args); err != nil {
			return app.Fatal(1, err)
		}

		wd, err := os.Getwd()
		if err != nil {
			return fmt.Errorf("acquire work directory failed: %v", err)
		}

		// load parent pom
		p, err := pom.NewPom(filepath.Join(wd, "pom.xml"))
		if err != nil {
			return err
		}

		// check args
		var name string
		if len(ctx.Args()) == 0 {
			if p == nil {
				return fmt.Errorf("module name is missing")
			}
			name = fmt.Sprintf("%v-%v", p.GetArtifactID(), moduleType)
		} else {
			name = ctx.Args()[0]
		}

		// build template data
		if args.Group == "" && p != nil {
			args.Group = p.GetGroupID()
		}
		if args.Group == "" {
			return fmt.Errorf("group arg is missing")
		}
		if args.Artifact == "" {
			args.Artifact = name
		}
		if args.Package == "" {
			args.Package = args.Group + "." + strings.Replace(args.Artifact, "-", ".", -1)
		}
		data := map[string]string{
			"Type":       moduleType,
			"GroupID":    args.Group,
			"ArtifactID": args.Artifact,
			"Package":    args.Package,
		}

		// check dir exist
		moduleDir := filepath.Join(wd, name)
		_, err = os.Stat(moduleDir)
		if err == nil {
			return fmt.Errorf("directory already exist: %v", moduleDir)
		}

		// create empty dirs
		var dirs []string
		if moduleType == "web" {
			dirs = append(dirs, filepath.Join(wd, name, "src", "main", "resources", "view"))
			dirs = append(dirs, filepath.Join(wd, name, "src", "main", "resources", "static", "js"))
			dirs = append(dirs, filepath.Join(wd, name, "src", "main", "resources", "static", "css"))
		}
		dirs = append(dirs, filepath.Join(wd, name, "src", "test", "java"))
		file.CreateDir(dirs...)

		fp := func(name string) string {
			return fmt.Sprintf("modules/%s/%s", moduleType, name)
		}

		// create files
		files := make(map[string]string)
		files[filepath.Join(moduleDir, "pom.xml")] = fp("pom.xml")
		switch moduleType {
		case "service":
			data["CleanArtifactID"] = strings.TrimSuffix(data["ArtifactID"], "-service")
			data["CleanPackage"] = strings.TrimSuffix(data["Package"], ".service")
			files[filepath.Join(moduleDir, "src", "main", "resources", "application.yml")] = fp("application.yml")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("Bootstrap.java").String()] = fp("Bootstrap.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("dao", "TestDao.java").String()] = fp("TestDao.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("biz", "TestBiz.java").String()] = fp("TestBiz.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("entity", "TestObject.java").String()] = fp("TestObject.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("impl", "TestServiceImpl.java").String()] = fp("TestServiceImpl.java")
			files[file.NewPath(moduleDir, "src", "test", "java").Join(strings.Split(args.Package, ".")...).Join("TestServiceTests.java").String()] = fp("TestServiceTests.java")

		case "service-contract":
			data["CleanArtifactID"] = strings.TrimSuffix(data["ArtifactID"], "-contract")
			data["CleanPackage"] = strings.TrimSuffix(data["Package"], ".contract")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("constant", "TestType.java").String()] = fp("TestType.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("dto", "TestDto.java").String()] = fp("TestDto.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("iface", "TestService.java").String()] = fp("TestService.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("config", "ServiceAutoConfiguration.java").String()] = fp("ServiceAutoConfiguration.java")
			files[filepath.Join(moduleDir, "src", "main", "resources", "META-INF", "spring.factories")] = fp("spring.factories")
			data["ServiceName"] = strings.TrimSuffix(data["ArtifactID"], "-contract")

		case "api":
			data["CleanArtifactID"] = strings.TrimSuffix(data["ArtifactID"], "-api")
			data["CleanPackage"] = strings.TrimSuffix(data["Package"], ".api")
			files[filepath.Join(moduleDir, "src", "main", "resources", "application.yml")] = fp("application.yml")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("Bootstrap.java").String()] = fp("Bootstrap.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("biz", "TestBiz.java").String()] = fp("TestBiz.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("controller", "TestController.java").String()] = fp("TestController.java")

		case "api-contract":
			data["CleanArtifactID"] = strings.TrimSuffix(data["ArtifactID"], "-contract")
			data["CleanPackage"] = strings.TrimSuffix(data["Package"], ".contract")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("vo", "TestVo.java").String()] = fp("TestVo.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("iface", "TestApi.java").String()] = fp("TestApi.java")

		case "msg-handler":
			data["CleanArtifactID"] = strings.TrimSuffix(data["ArtifactID"], "-msg-handler")
			data["CleanPackage"] = strings.TrimSuffix(data["Package"], ".msg.handler")
			files[filepath.Join(moduleDir, "src", "main", "resources", "application.yml")] = fp("application.yml")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("Bootstrap.java").String()] = fp("Bootstrap.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("executor", "TestExecutor.java").String()] = fp("TestExecutor.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("biz", "TestBiz.java").String()] = fp("TestBiz.java")

		case "msg-contract":
			data["CleanArtifactID"] = strings.TrimSuffix(data["ArtifactID"], "-msg-contract")
			data["CleanPackage"] = strings.TrimSuffix(data["Package"], ".msg.contract")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("publisher", "TestPublisher.java").String()] = fp("TestPublisher.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("topic", "TestTopic.java").String()] = fp("TestTopic.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("msg", "TestMessage.java").String()] = fp("TestMessage.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("handler", "TestHandler.java").String()] = fp("TestHandler.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("config", "PublisherAutoConfiguration.java").String()] = fp("PublisherAutoConfiguration.java")
			files[filepath.Join(moduleDir, "src", "main", "resources", "META-INF", "spring.factories")] = fp("spring.factories")

		case "task":
			data["CleanArtifactID"] = strings.TrimSuffix(data["ArtifactID"], "-task")
			data["CleanPackage"] = strings.TrimSuffix(data["Package"], ".task")
			files[filepath.Join(moduleDir, "src", "main", "resources", "application.yml")] = fp("application.yml")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("Bootstrap.java").String()] = fp("Bootstrap.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("executor", "TestExecutor.java").String()] = fp("TestExecutor.java")
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("biz", "TestBiz.java").String()] = fp("TestBiz.java")

		case "web":
			files[file.NewPath(moduleDir, "src", "main", "java").Join(strings.Split(args.Package, ".")...).Join("controller", "TestController.java").String()] = fp("TestController.java")
			files[file.NewPath(moduleDir, "src", "test", "java").Join(strings.Split(args.Package, ".")...).Join("executor", "TestControllerTests.java").String()] = fp("TestControllerTests.java")
		}
		if err = tpl.Execute(files, data); err != nil {
			return err
		}

		// modify files
		if p != nil {
			p.AddModule(name)
		}

		fmt.Println("finished.")
		return nil
	})
	cmd.Flags.Register(flag.Help)
	cmd.Flags.String("group", "g", "", "group id")
	cmd.Flags.String("artifact", "a", "", "artifact id")
	cmd.Flags.String("package", "p", "", "package")
	return cmd
}
