package ci

import (
	"github.com/cuigh/lark/util/file"
)

type Gitlab struct {
	filename string
}

func NewGitlab(filename string) (*Gitlab, error) {
	if file.NotExist(filename) {
		return nil, nil
	}

	return &Gitlab{
		filename: filename,
	}, nil
}
