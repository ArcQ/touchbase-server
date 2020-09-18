import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBase, defaultValue } from 'app/shared/model/base.model';

export const ACTION_TYPES = {
  FETCH_BASE_LIST: 'base/FETCH_BASE_LIST',
  FETCH_BASE: 'base/FETCH_BASE',
  CREATE_BASE: 'base/CREATE_BASE',
  UPDATE_BASE: 'base/UPDATE_BASE',
  DELETE_BASE: 'base/DELETE_BASE',
  RESET: 'base/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBase>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type BaseState = Readonly<typeof initialState>;

// Reducer

export default (state: BaseState = initialState, action): BaseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BASE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BASE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BASE):
    case REQUEST(ACTION_TYPES.UPDATE_BASE):
    case REQUEST(ACTION_TYPES.DELETE_BASE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_BASE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BASE):
    case FAILURE(ACTION_TYPES.CREATE_BASE):
    case FAILURE(ACTION_TYPES.UPDATE_BASE):
    case FAILURE(ACTION_TYPES.DELETE_BASE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BASE_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_BASE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BASE):
    case SUCCESS(ACTION_TYPES.UPDATE_BASE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BASE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/bases';

// Actions

export const getEntities: ICrudGetAllAction<IBase> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BASE_LIST,
    payload: axios.get<IBase>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IBase> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BASE,
    payload: axios.get<IBase>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBase> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BASE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IBase> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BASE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBase> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BASE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
