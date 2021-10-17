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

const Main = styled.main`
  display: flex;
  height: 100%;
`

const Nav = styled.nav`
  width: 200px;
  background-color: #0077b6;
  ul {
    padding-left: 0;
    list-style: none;

    li {
      height: 30px;
      padding-left: 30px;
      transition: background-color 0.2s ease-in-out;
      a {
        display: inline-block;
        width: 100%;
        color: #90e0ef;
        text-decoration: none;
        vertical-align: middle;
        transition: color 0.2s ease-in-out;
      }

      &:hover {
        background-color: #00b4d8;
        a {
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
              <Link to="/dashboard">Dashboard</Link>
            </li>
            <li>
              <Link to="/reviews">Reviews</Link>
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
