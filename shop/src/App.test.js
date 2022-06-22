import App from './App';
import React from 'react'
import nock from 'nock'
import {render, fireEvent, waitFor, screen} from '@testing-library/react'
import '@testing-library/jest-dom/extend-expect'

test('renders learn react link', () => {
  render(<App/>);
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});

test('where low risk payment is confirmed', async () => {
  render(<App/>)

  nock("http://localhost")
      .post('/pay', {user: 'Athena', amount: 5.99})
      .once()
      .reply(200, "OK");

  fireEvent.change(screen.getByLabelText('user-name'), {target: {value: 'Athena'}})
  fireEvent.click(screen.getByLabelText('buy-coffee'))

  await waitFor(() => screen.getByLabelText('payment-success'))

  expect(screen.getByLabelText('message')).toHaveTextContent('OK')
})

test('where high risk payment needs 2FA', async () => {
  render(<App/>)

  nock("http://localhost")
      .post('/pay', {user: 'Riko', amount: 20000})
      .once()
      .reply(200, "2fa required");

  fireEvent.change(screen.getByLabelText('user-name'), {target: {value: 'Riko'}})
  fireEvent.click(screen.getByLabelText('buy-car'))

  await waitFor(() => screen.getByLabelText('2fa-required'))

  expect(screen.getByLabelText('message')).toHaveTextContent('OK')
})