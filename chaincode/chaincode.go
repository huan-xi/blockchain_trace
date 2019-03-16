package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"github.com/hyperledger/fabric/protos/peer"
)

/*
安装链代码
peer chaincode install -p github.com/chaincode/trace -n cc -v 0
示例化
peer chaincode instantiate -n cc -v 0 -c '{"Args":[]}' -C tracechannel
调用方法
peer chaincode invoke -n cc -c '{"Args":["initMilk", "milk1", "bob","data"]}' -C tracechannel
peer chaincode invoke -n cc -c '{"Args":["readMilk", "milk1"]}' -C tracechannel
调用查询
peer chaincode query -n cc -c '{"Args":["readMilk", "milk1"]}' -C tracechannel
更新
peer chaincode install -p github.com/chaincode/trace -n cc -v 0.1
peer chaincode upgrade -n cc -v 0.1 -c '{"Args":[]}' -C tracechannel
*/

type MilkChaincode struct {
}

type milk struct {
	Name           string `json:"name"`
	Owner          string `json:"owner"`
	Data           string `json:"data"`
	ProcessInfo    string `json:"process_info"`
	InspectionInfo string `json:"inspection_info"`
}

//初始化方法
func (MilkChaincode) Init(stub shim.ChaincodeStubInterface) peer.Response {
	return shim.Success(nil)
}

//方法调用入口
func (t *MilkChaincode) Invoke(stub shim.ChaincodeStubInterface) peer.Response {
	function, args := stub.GetFunctionAndParameters()
	fmt.Println("invoke is running " + function)
	if function == "initMilk" {
		return t.initMilk(stub, args)
	} else if function == "readMilk" {
		return t.readMilk(stub, args)
	} else if function == "queryMilksByOwner" {
		return t.queryMilksByOwner(stub, args)
	} else if function == "processMilk" {
		return t.processMilk(stub, args)
	} else if function == "inspectionMilk" {
		return t.inspectionMilk(stub, args)
	}
	return shim.Error("no this invoke function:" + function)
}

//=====================================
//添加一个奶粉资产
func (t *MilkChaincode) initMilk(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	//milk1 bob weight:100
	//===== 输入参数检测
	if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 3")
	}
	milkName := args[0]
	owner := args[1]
	data := args[2]
	// ==== 检查milk name是否存在 ====
	milkAsBytes, err := stub.GetState(milkName)
	if err != nil {
		return shim.Error("Failed to get milk: " + err.Error())
	} else if milkAsBytes != nil {
		fmt.Println("This milk already exists: " + milkName)
		return shim.Error("This milk already exists: " + milkName)
	}
	if err != nil {
		return shim.Error(err.Error())
	}
	// ==== 创建奶粉json 对象 ====
	//objectType := "milk"
	milk := &milk{milkName, owner, data, "", ""}
	milkJSONasBytes, err := json.Marshal(milk)
	if err != nil {
		return shim.Error(err.Error())
	}
	err = stub.PutState(milkName, milkJSONasBytes) //写入区块链
	return shim.Success(nil)
}

//查看奶粉信息
func (t *MilkChaincode) readMilk(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	var name, jsonResp string
	var err error
	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting name of the milk to query")
	}
	name = args[0]
	valAsbytes, err := stub.GetState(name) //get the milk from chaincode state
	if err != nil {
		jsonResp = "{\"Error\":\"Failed to get state for " + name + "\"}"
		return shim.Error(jsonResp)
	} else if valAsbytes == nil {
		jsonResp = "{\"Error\":\"milk does not exist: " + name + "\"}"
		return shim.Error(jsonResp)
	}
	return shim.Success(valAsbytes)
}

//通过所有者查询奶粉信息
func (t *MilkChaincode) queryMilksByOwner(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	//   0
	// "bob"
	if len(args) < 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}
	owner := args[0]
	queryString := fmt.Sprintf("{\"selector\":{\"owner\":\"%s\"}}", owner)
	queryResults, err := getQueryResultForQueryString(stub, queryString)
	if err != nil {
		return shim.Error(err.Error())
	}
	return shim.Success(queryResults)
}

//修改数据
func (t *MilkChaincode) updateMilk(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	// milk1 poseo_nfm
	//===== 输入参数检测
	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}
	milkName := args[0]
	data := args[1]
	// ==== 检查milk name是否存在 ====
	milkAsBytes, err := stub.GetState(milkName)
	if err != nil {
		return shim.Error("Failed to get milk: " + err.Error())
	} else if milkAsBytes == nil {
		fmt.Println("This milk not exists: " + milkName)
		return shim.Error("This milk not exists: " + milkName)
	}
	if err != nil {
		return shim.Error(err.Error())
	}
	milkToTransfer := milk{}
	err = json.Unmarshal(milkAsBytes, &milkToTransfer) //unmarshal it aka JSON.parse()
	if err != nil {
		return shim.Error(err.Error())
	}
	milkToTransfer.Data = data

	marbleJSONasBytes, _ := json.Marshal(milkToTransfer)
	err = stub.PutState(milkName, marbleJSONasBytes) //rewrite the marble
	if err != nil {
		return shim.Error(err.Error())
	}
	fmt.Println("- end transferMarble (success)")
	return shim.Success(nil)
}

//修改检测信息
func (t *MilkChaincode) inspectionMilk(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	// milk1 poseo_nfm
	//===== 输入参数检测
	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}
	milkName := args[0]
	inspectionInfo := args[1]
	// ==== 检查milk name是否存在 ====
	milkAsBytes, err := stub.GetState(milkName)
	if err != nil {
		return shim.Error("Failed to get milk: " + err.Error())
	} else if milkAsBytes == nil {
		fmt.Println("This milk not exists: " + milkName)
		return shim.Error("This milk not exists: " + milkName)
	}
	if err != nil {
		return shim.Error(err.Error())
	}
	milkToTransfer := milk{}
	err = json.Unmarshal(milkAsBytes, &milkToTransfer) //unmarshal it aka JSON.parse()
	if err != nil {
		return shim.Error(err.Error())
	}
	milkToTransfer.InspectionInfo = inspectionInfo

	marbleJSONasBytes, _ := json.Marshal(milkToTransfer)
	err = stub.PutState(milkName, marbleJSONasBytes) //rewrite the marble
	if err != nil {
		return shim.Error(err.Error())
	}
	fmt.Println("- end transferMarble (success)")
	return shim.Success(nil)
}

//修改加工信息
func (t *MilkChaincode) processMilk(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	// milk1 poseo_nfm
	//===== 输入参数检测
	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}
	milkName := args[0]
	process_info := args[1]
	// ==== 检查milk name是否存在 ====
	milkAsBytes, err := stub.GetState(milkName)
	if err != nil {
		return shim.Error("Failed to get milk: " + err.Error())
	} else if milkAsBytes == nil {
		fmt.Println("This milk not exists: " + milkName)
		return shim.Error("This milk not exists: " + milkName)
	}
	if err != nil {
		return shim.Error(err.Error())
	}
	milkToTransfer := milk{}
	err = json.Unmarshal(milkAsBytes, &milkToTransfer) //unmarshal it aka JSON.parse()
	if err != nil {
		return shim.Error(err.Error())
	}
	milkToTransfer.ProcessInfo = process_info

	marbleJSONasBytes, _ := json.Marshal(milkToTransfer)
	err = stub.PutState(milkName, marbleJSONasBytes) //rewrite the marble
	if err != nil {
		return shim.Error(err.Error())
	}
	fmt.Println("- end transferMarble (success)")
	return shim.Success(nil)
}

func getQueryResultForQueryString(stub shim.ChaincodeStubInterface, queryString string) ([]byte, error) {

	fmt.Printf("- getQueryResultForQueryString queryString:\n%s\n", queryString)
	resultsIterator, err := stub.GetQueryResult(queryString)
	if err != nil {
		return nil, err
	}
	defer resultsIterator.Close()
	buffer, err := constructQueryResponseFromIterator(resultsIterator)
	if err != nil {
		return nil, err
	}
	fmt.Printf("- getQueryResultForQueryString queryResult:\n%s\n", buffer.String())
	return buffer.Bytes(), nil
}
func constructQueryResponseFromIterator(resultsIterator shim.StateQueryIteratorInterface) (*bytes.Buffer, error) {
	// buffer is a JSON array containing QueryResults
	var buffer bytes.Buffer
	buffer.WriteString("[")
	bArrayMemberAlreadyWritten := false
	for resultsIterator.HasNext() {
		queryResponse, err := resultsIterator.Next()
		if err != nil {
			return nil, err
		}
		// Add a comma before array members, suppress it for the first array member
		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		buffer.WriteString("{\"Key\":")
		buffer.WriteString("\"")
		buffer.WriteString(queryResponse.Key)
		buffer.WriteString("\"")

		buffer.WriteString(", \"Record\":")
		// Record is a JSON object, so we write as-is
		buffer.WriteString(string(queryResponse.Value))
		buffer.WriteString("}")
		bArrayMemberAlreadyWritten = true
	}
	buffer.WriteString("]")

	return &buffer, nil
}
func main() {
	err := shim.Start(new(MilkChaincode))
	if err != nil {
		fmt.Printf("Error starting Simple chaincode: %s", err)
	}
}
