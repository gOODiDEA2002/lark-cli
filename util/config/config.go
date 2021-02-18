package config

import (
	"log"

	"github.com/cuigh/lark/util/file"
	"gopkg.in/yaml.v2"
)

type Config struct {
	filename   string
	configInfo *ConfigInfo
}

type ConfigInfo struct {
	Group    string `yaml:"group"`
	Artifact string `yaml:"artifact"`
	Port     struct {
		Api          int `yaml:"api"`
		AdminApi     int `yaml:"admin-api"`
		Service      int `yaml:"service"`
		AdminService int `yaml:"admin-service"`
		MsgHandler   int `yaml:"msg-handler"`
		Task         int `yaml:"task"`
	}
}

func NewConfig(filename string) (*Config, error) {
	if file.NotExist(filename) {
		return nil, nil
	}

	fileContent, err := file.Get(filename)
	if err != nil {
		return nil, err
	}
	//
	var configInfo ConfigInfo

	err = yaml.Unmarshal([]byte(fileContent), &configInfo)
	if err != nil {
		log.Fatalf("cannot unmarshal data: %v", err)
	}
	//
	return &Config{
		filename:   filename,
		configInfo: &configInfo,
	}, nil
}

// GetConfigInfo 获取 配置信息 属性
func (c *Config) GetConfigInfo() *ConfigInfo {
	return c.configInfo
}
