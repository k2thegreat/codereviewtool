import { Reviews } from './components/reviews';
import { Dashboard } from './components/dashboard';
import { NotFound } from './components/notFound';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import styled from 'styled-components';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChartBar, faUsers } from '@fortawesome/free-solid-svg-icons'

const Main = styled.main`
  display: flex;
  height: 100%;
`

const Nav = styled.nav`
  z-index: 1;
  width: 200px;
  background-color: #0077b6;
  box-shadow: 2px 0 4px 1px rgba(0, 119, 182, 0.4);

  ul {
    padding-left: 0;
    list-style: none;
    margin-top: 50px;

    li {
      height: 32px;
      padding-left: 20px;
      padding-right: 20px;
      transition: background-color 0.2s ease-in-out;

      svg {
        transition: color 0.2s ease-in-out;
      }

      a {
        display: inline-block;
        width: 100%;
        padding: 5px;
        color: #90e0ef;
        outline: none;
        text-decoration: none;
        vertical-align: middle;
        transition: color 0.2s ease-in-out;

        &:focus {
          outline: 1px solid #90e0ef;
        }
      }

      &:hover {
        background-color: #00b4d8;
        a, svg {
          color: white;
        }
      }
    }
  }
`

const Section = styled.section`
  width: calc(100vw - 200px);
  height: 100vh;
  background-color: #caf0f8;
`

function App() {
  return (
    <Main>
      <Router>
        <Nav>
          <ul>
            <li>
              <Link to="/dashboard"><FontAwesomeIcon color="#90e0ef" icon={faChartBar} size="lg" /> &nbsp; Dashboard</Link>
            </li>
            <li>
              <Link to="/reviews"><FontAwesomeIcon color="#90e0ef" icon={faUsers} size="lg" /> &nbsp; Reviews</Link>
            </li>
          </ul>
        </Nav>
        <Section>
          <Switch>
            <Route exact path="/">
              <Dashboard />
            </Route>
            <Route path="/dashboard">
              <Dashboard />
            </Route>
            <Route path="/reviews">
              <Reviews />
            </Route>
            <Route path="*">
              <NotFound />
            </Route>
          </Switch>
        </Section>
      </Router>
    </Main>
  );
}

export default App;
