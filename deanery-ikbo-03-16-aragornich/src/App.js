import React from 'react';
import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import { Table, Button } from 'react-bootstrap';
import Request from './Services/Request'

class JournalTable extends React.Component {
    render() {
        let journal = this.props.journal;
        return (
            <Table striped bordered hover size="sm" variant="dark">
                <thead className="text-white">
                <tr>
                    <th>#</th>
                    <th>ФИО</th>
                    <th>ПрИС</th>
                    <th>СИИ</th>
                    <th>ПИ</th>
                    <th>НСИБ</th>
                    <th>СисАнал</th>
                    <th>РБД</th>
                    <th>СПО</th>
                    <th>Пересдачи</th>
                </tr>
                </thead>
                <tbody className="text-white">
                {this.props.students.map((student) => {
                        var groupId = this.props.group;
                        if (student.studyGroupId !== groupId)
                            return;
                        var PrIS, SII, PI, NSIB, SisAnal, RBD, SPO;
                        journal.map(row => {
                            if (row.studentId !== student.id) return;
                            else if (row.studyPlanId === 1) PrIS = 6 - row.markId;
                            else if (row.studyPlanId === 2) SII = 6 - row.markId;
                            else if (row.studyPlanId === 3) PI = 6 - row.markId;
                            else if (row.studyPlanId === 4) NSIB = row.markId == '5' ? "Зачет" : "Незачет";
                            else if (row.studyPlanId === 5) SisAnal = 6 - row.markId;
                            else if (row.studyPlanId === 6) RBD = row.markId == '5' ? "Зачет" : "Незачет";
                            else if (row.studyPlanId === 7) SPO = 6 - row.markId;

                        });
                        let color1 = PrIS < 3 ? "text-danger" : "";
                        let color2 = SII < 3 ? "text-danger" : "";
                        let color3 = PI < 3 ? "text-danger" : "";
                        let color4 = NSIB === "Незачет" ? "text-danger" : "";
                        let color5 = SisAnal < 3 ? "text-danger" : "";
                        let color6 = RBD === "Незачет" ? "text-danger" : "";
                        let color7 = SPO < 3 ? "text-danger" : "";
                        let count = PrIS < 3 ? 1 : 0;
                        if (SII < 3) count++;
                        if (PI < 3) count++;
                        if (NSIB === "Незачет") count++;
                        if (SisAnal < 3) count++;
                        if (RBD === "Незачет") count++;
                        if (SPO < 3) count++;
                        return (
                            <tr key={student.id}>
                                <th>{student.id}</th>
                                <th>{student.surName + " " + student.name + " " + student.secondName}</th>
                                <th className={color1}>{PrIS}</th>
                                <th className={color2}>{SII}</th>
                                <th className={color3}>{PI}</th>
                                <th className={color4}>{NSIB}</th>
                                <th className={color5}>{SisAnal}</th>
                                <th className={color6}>{RBD}</th>
                                <th className={color7}>{SPO}</th>
                                <th>{count}</th>
                            </tr>
                        )
                    }
                )}
                </tbody>
            </Table>
        )
    }
}

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            activeGroup: null,
            students: null,
            journal: null
        };
        this.requestFromServer = this.requestFromServer.bind(this);
    }

    componentDidMount() {
        this.requestFromServer();
    }

    requestFromServer() {
        Request.getJournal().then(j => this.setState({journal: j}));
        Request.getStudents().then(s => this.setState({students: s}));
    }

    componentWillUnmount() {
        this.setState({journal: null});
        this.setState({students: null});
    }

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo" />
                    <br/><br/>
                    <div>
                        {this.state.students
                            ? <JournalTable
                                students={this.state.students}
                                group={this.state.activeGroup}
                                journal={this.state.journal}/>
                            : null}
                        <Button variant="danger" size="lg" onClick={() => {
                            this.setState({activeGroup: 1});
                        }}>
                            ИКБО-02-16
                        </Button>
                        <Button variant="info" size="lg" style={{marginLeft: 10}} onClick={() => {
                            this.setState({activeGroup: 2});
                        }}>
                            ИКБО-03-16
                        </Button>
                    </div>
                </header>
            </div>
        );
    }
}
export default App;