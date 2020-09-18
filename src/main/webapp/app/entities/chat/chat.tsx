import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './chat.reducer';
import { IChat } from 'app/shared/model/chat.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IChatProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Chat = (props: IChatProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { chatList, match, loading } = props;
  return (
    <div>
      <h2 id="chat-heading">
        <Translate contentKey="touchbaseApp.chat.home.title">Chats</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="touchbaseApp.chat.home.createLabel">Create new Chat</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {chatList && chatList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.chat.createdAt">Created At</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.chat.updatedAt">Updated At</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.chat.chatpiChatId">Chatpi Chat Id</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.chat.base">Base</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {chatList.map((chat, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${chat.id}`} color="link" size="sm">
                      {chat.id}
                    </Button>
                  </td>
                  <td>{chat.createdAt ? <TextFormat type="date" value={chat.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{chat.updatedAt ? <TextFormat type="date" value={chat.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{chat.chatpiChatId}</td>
                  <td>{chat.baseId ? <Link to={`base/${chat.baseId}`}>{chat.baseId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${chat.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${chat.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${chat.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="touchbaseApp.chat.home.notFound">No Chats found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ chat }: IRootState) => ({
  chatList: chat.entities,
  loading: chat.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Chat);
